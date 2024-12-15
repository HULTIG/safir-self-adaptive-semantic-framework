# Use a base Python image
FROM python:3.10.12-slim

# Set the working directory
WORKDIR /app

# Install system dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    build-essential \
    libpq-dev && \
    rm -rf /var/lib/apt/lists/*

# Copy the requirements file if available
COPY requirements.txt .

RUN pip install --no-cache-dir torch==2.5.1

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Install Jupyter Notebook
RUN pip install jupyter

# Expose port for Jupyter Notebook
EXPOSE 8888

# Copy notebook files
COPY . .

# Start Jupyter Notebook
CMD ["jupyter", "notebook", "--ip=0.0.0.0", "--port=8888", "--no-browser", "--allow-root"]
