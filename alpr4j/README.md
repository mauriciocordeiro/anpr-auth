# alpr4j

![GitHub release (latest by date)](https://img.shields.io/github/v/release/mauriciocordeiro/alpr4j)
[![Build Status](https://travis-ci.org/mauriciocordeiro/alpr4j.svg?branch=master)](https://travis-ci.org/mauriciocordeiro/alpr4j)
[![GitHub issues](https://img.shields.io/github/issues/mauriciocordeiro/alpr4j)](https://github.com/mauriciocordeiro/alpr4j/issues)
[![GitHub license](https://img.shields.io/github/license/mauriciocordeiro/alpr4j)](https://github.com/mauriciocordeiro/alpr4j/blob/master/LICENSE)


API para o [OpenALPR](https://github.com/openalpr/openalpr).

## Configurações

1. Instale o OpenALPR localmente, seguindo as intruções fornecidas [aqui](https://github.com/openalpr/openalpr#binaries).

2. Copie o diretório [`runtime_data/`](https://github.com/openalpr/openalpr/tree/master/runtime_data) para o diretório local equivalente.

3. Configure os parâmetros em _application.properties_

```
# OpenALPR

## ativa o uso dos parâmetros abaixo
br.org.mcord.alpr4j.useDefault=false

## nacionalidade da placa
br.org.mcord.alpr4j.country=br

## padrão da placa (`br` ou `mercosul`)
br.org.mcord.alpr4j.pattern=br 

## nº máximo de resutados
br.org.mcord.alpr4j.topN=3

## se `true`, retorna apenas os resultados que batem com o padrão
br.org.mcord.alpr4j.onlyPatternMatches=true

## sinaliza o uso do OpenALPR via WSL (Windows Subsystem for Linux) no Win10
br.org.mcord.alpr4j.wsl=false 
```

4. Implante a API.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Este é um projeto Maven, compilado para o formato `.war` e  com _deploy_ feito no Tomcat8.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No Linux, crie o diretório `/alpr4j` de propriedade do usuário `tomcat8`. É neste diretório que o banco de dados da API será criado, e onde as imagens das placas serão temporariamente armazenadas enquanto são analizadas.

```shell
$ sudo mkdir /alpr4j
$ sudo chown -R tomcat8:tomcat8 /alpr4j
```

Se desejar, compile e treine o OpenALPR.

### Compilando o OpenALPR

Para compilar o OpenALPR, siga as instruções fornecidas [aqui](https://github.com/openalpr/openalpr/wiki).

#### Treinando o OpenALPR

Caso queira treinar o OpenALPR, siga as instruções fornecidas [aqui](http://doc.openalpr.com/opensource.html#training-the-detector). 

É necessário instalar o OpenCV. Veja como [aqui](https://www.pyimagesearch.com/2016/10/24/ubuntu-16-04-how-to-install-opencv/).

## Utilização

Uma vez configurado, basta enviar uma requisição como se segue:

```HTTP
POST /v1/find HTTP/1.1
Host: <endereço_do_host>
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="image"; filename="<imagem>"
Content-Type: image/jpeg

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
```

A API deve retornar o seguinte:

```JSON
{
  "imgHeight": 0,
  "imgWidth": 0,
  "processingTimeMillis": 0,
  "results": [
    {
      "confidence": 0,
      "matchesPattern": true,
      "pattern": "string",
      "plate": "string"
    }
  ]
}
```

Se nenhuma placa for reconhecida, `results` estará vazio (`{ "results": [] }`).

### Segurança

JWT Bearer Token

## Docker

Para executar o **alpr4j** utilizando o conteúdo padrão (do _branch_ `master`), basta montar o contêiner Docker, como segue:

1. Verifique se os arquivos _Dockerfile_ e _docker-compose.yml_ estão presentes na raíz do projeto;

2. Execute o comando `docker-compose up -d --build`;

3. Acesse [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) para consultar a documentação da API.
