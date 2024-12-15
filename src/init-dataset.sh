#!/bin/bash
# Wait for Fuseki to start (optional, adjust timing if needed)
sleep 10

# Create a dataset (replace 'myDataset' with your desired dataset name)
curl --user admin:${ADMIN_PASSWORD:-admin} \
  --data "dbName=myDataset&dbType=tdb2" \
  http://localhost:3030/$/datasets
