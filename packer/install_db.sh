#!/bin/bash

# Install PostgreSQL
sudo yum install -y postgresql-server postgresql-contrib || exit 1

# Initialize database (consider adding a password prompt)
sudo postgresql-setup initdb || exit 1

#sudo -u postgres psql --command '\password postgres'

# Enable and start PostgreSQL service
sudo systemctl enable postgresql || exit 1
sudo systemctl start postgresql || exit 1

sudo firewall-cmd --add-port=5432/tcp --permanent || exit 1
sudo firewall-cmd --reload || exit 1


sudo useradd -m postgres

# Change the ownership of the PostgreSQL data directory to the postgres user
sudo chown -R postgres:postgres /var/lib/pgsql/data

# Set the password for the postgres user
sudo passwd postgres
echo 'postgres' | sudo passwd --stdin postgres

# Open firewall port (prompt for password or use another method)


# Check if the role exists before creating it
if sudo -u postgres psql -tAc "SELECT 1 FROM pg_roles WHERE rolname='postgres'" | grep -q 1; then
	  sudo psql -c "ALTER USER postgres WITH PASSWORD 'postgres';" || exit 1
    echo "Role 'postgres' already exists."
else
    # Create database and user (use stronger password)
    sudo -u postgres psql -c "CREATE ROLE postgres WITH LOGIN PASSWORD 'postgres';" || exit 1
    sudo -u postgres psql -c "ALTER ROLE postgres CREATEDB;" || exit 1
fi

sudo -u postgres createdb test_db

# Create database (if it doesn't already exist)
if sudo -u postgres psql -lqt | cut -d \| -f 1 | grep -qw test_db; then
    echo "Database 'test_db' already exists."
else
    sudo -u postgres psql -c "CREATE DATABASE test_db;" || exit 1
fi

#sudo chown -R csye6225:csye6225 /var/

# Grant privileges
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE test_db TO postgres;" || exit 1

# Log execution details
echo "PostgreSQL installation and configuration complete." >> /var/log/postgresql_setup.log

exit 0
