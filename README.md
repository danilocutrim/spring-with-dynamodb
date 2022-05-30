# Spring with dynamodb

### Instalação

- **Acessar a pasta do projeto:**


- **Executar script para o docker compose:**

  Comando: ```docker-compose up```


- **Verificar localstack (DynamoDB...)**

  Endereço: http://localhost:4566/health


- **Configurar AWS localstack**

  Comando: ```aws configure```

  Informa os seguintes dados:

  ```
  Key ID: 123
  Access Key: 123
  Region: us-east-1
  Format: text
  ```

- **Criar a tabela de usuarios:**

  Comando: ```aws dynamodb create-table --cli-input-json file://script-file/user-table.json --endpoint-url=http://localhost:4566```
