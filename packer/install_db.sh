#!/bin/bash

sudo yum install -y postgresql-server postgresql-contrib || exit 1

# Initialize database (consider adding a password prompt)
sudo postgresql-setup initdb || exit 1

# Enable and start PostgreSQL service
sudo systemctl enable postgresql || exit 1
sudo systemctl start postgresql || exit 1

# Open firewall port (prompt for password or use another method)
sudo firewall-cmd --add-port=5432/tcp --permanent || exit 1
sudo firewall-cmd --reload || exit 1

sudo useradd -m postgres
echo 'postgres' | sudo passwd --stdin postgres


# Check if the role exists before creating it
if sudo -u postgres psql -tAc "SELECT 1 FROM pg_roles WHERE rolname='postgres'" | grep -q 1; then
	sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'postgres';" || exit 1
    echo "Role 'postgres' already exists."
else
    # Create database and user (use stronger password)
    sudo -u postgres psql -c "CREATE ROLE postgres WITH LOGIN PASSWORD 'postgres';" || exit 1
    sudo -u postgres psql -c "ALTER ROLE postgres CREATEDB;" || exit 1
fi

# Create database (if it doesn't already exist)
if sudo -u postgres psql -lqt | cut -d \| -f 1 | grep -qw test_db; then
    echo "Database 'test_db' already exists."
else
    sudo -u postgres psql -c "CREATE DATABASE test_db;" || exit 1
fi

# Grant privileges
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE test_db TO postgres;" || exit 1

# Log execution details
echo "PostgreSQL installation and configuration complete." >> sudo -u postgres tee -a /var/log/postgresql_setup.log

sudo sed -i 's%host\s\+replication\s\+all\s\+127\.0\.0\.1\/32\s\+ident%host    replication          all          127.0.0.1/32            md5%g' /var/lib/pgsql/data/pg_hba.conf

sudo systemctl restart postgresql

exit 0

