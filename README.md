# anpr-auth
Vehicle authentication based on ANPR

---

## Instruções

### Docker

Para executar os sistemas via Docker, é necessário ter o docker e docker-compose instalado (Linux) ou o Docker com WSL2 (Windows 10).

**Construir os contêiners:**

1. Clone este repositório
```shell
git clone https://github.com/mauriciocordeiro/anpr-auth.git

```
A estrutura do repositório deve ser
```bash
.
└── anpr-auth/
    ├── anpr-app/
    ├── anpr-ng/
    ├── check4j/
    ├── doc/
    ├── vehiclespy/
    ├── README.md
    └── docker-compose.yml
```


2. Acesse a pasta do repositório
```shell
cd anpr-auth
```

3. Construa e execute os contêiners
```shell
docker-compose up -d --build
```

**Executar**

Após a construção dos contêiners, os recursos estarão disponíveis da seguinte forma:

| RECURSO    | PORTA | DESCRIÇÃO                                      |
|------------|-------|------------------------------------------------|
| db         | 27017 | Banco de dados não relacional (MongoDB)        |
| alpr4j     | 8080  | API para reconhecimento de placas              |
| check4j    | 8081  | API para verificação de permissões             |
| vehiclespy | 5001  | API para gerenciamento do cadastro de veículos |
| anpr-ng    | 4000  | _Frontend_ da plataforma                       |

Acesse [localhost:4000](http://localhost:4000/) para começar.
