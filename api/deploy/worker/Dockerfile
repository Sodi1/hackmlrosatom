FROM python:3.10.11-slim-buster


RUN pip install poetry==1.4.2

# Configuring poetry
RUN poetry config virtualenvs.create false

# Copying requirements of a project
COPY worker/pyproject.toml worker/poetry.lock /app/src/
WORKDIR /app/src

# Installing requirements
RUN poetry install

# Copying actuall application
ADD ./worker /app/src/
RUN poetry install

CMD ["/usr/local/bin/python", "-m", "worker"]
