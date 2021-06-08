# app-mutants-validator
Aplicación para detectar si una persona tiene genes mutantes a partir de su cadena de ADN

---

## Entorno
- Java 11
- Spring Boot version 2.4.0
- Mongo version 4.4.0
- Maven version 3.6.3

---

## Ejecución con Docker Compose 
    
### Requisitos
- Docker Engine
- Docker Compose version 3
- git

### Configuración

- Abrir el archivo `.env` ubicado en la carpeta raíz del proyecto. 
  Editar las siguientes variables de entorno.
  
  - **PUBLISH_PORT**: está variable será el puerto que Docker publicará para el contenedor. 
    Es por este puerto donde la aplicación estará escuchando las peticiones. 
    
    **Ejemplo**
    ```
    PUBLISH_PORT=80
    ```
  - **SEQUENCES**: Cantidad de secuencias mutantes mínimas para que el ADN sea 
    considerado mutante.

    **Ejemplo**
    ```
    SEQUENCES=2
    ```  
  - **NUCLEOTIDE_NUMBER**: Cantidad de letras (nucleótidos) repetidas 
    en una secuencia para que sea considerada mutante.

    **Ejemplo**
    ```
    NUCLEOTIDE_NUMBER=4
    ```  


### Pasos

##### Paso 1.
```
git clone https://github.com/martinezhenry/app-mutants-validator
```

##### Paso 2.
```
cd app-mutants-validator
chmod u+x ./scrtips/run-app.sh
```

##### Paso 3.
###### Iniciando con script `run.app.sh` (Solo ambientes Linux)
```
./scripts/run.app.sh
```

###### Iniciando con docker compose (Windows y Linux) 
```
docker-compose up --force-recreate --build -d
```

> **Nota*: los parametros `--force-recreate`, `--build` y `-d` puede ser descartados.
> Se usan para forzar la reconstrucción de las imagenes de docker en caso de que estas 
> ya existen y para ejecutar en background.

---

## Uso

### Servicio Mutant 
Utilizado para detectar si el ADN es de un mutante.

#### URL:
- http://{server}:{port}/mutant

#### Petición

##### Método Http
- POST


##### Cabeceras
```
Content-Type: application/json
Content-Length: <longitud del cuerpo de la petición>
```

> Debe reemplazar `<longitud del cuerpo de la petición>` por el número de caracteres 
> que se envíen en el cuerpo de la petición


##### Cuerpo

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|dna|arreglo de secuencias de ADN que se desean validar|String[]|\["ATC", "ATC", "ATC"\]

**Ejemplo**
```
{ 
    "dna": ["ATC", "ATC", "ATC"]
}
```


##### Ejemplo de una petición con curl
```
curl --location --request POST 'http://127.0.0.1:80/mutant' \
--header 'Content-Type: application/json' \
--data-raw '{ 
    "dna": ["ATC", "ATC", "ATC"]
}'
```


### Respuesta Exitosa Mutante

##### Estatus HTTP Code
- 200 - OK

##### Cabeceras
```
Content-Type: application/json
X-REQUEST-ID: <Id de pa petición>
```

##### Cuerpo

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|requestId|id de la petición|String|A168DB26-4C1B-4A38-9635-4990192B5135
|datetime|fecha y hora de la petición|Date|2021-06-08T01:35:58.638+00:00
|status|Código del estado de la respuesta|Integer|200
|message|Mensaje descriptivo de la respuesta|String|OK
|specimen|Objeto representativo de un Specimen|Object|Ver -> [Especificaciones Specimen](#especificaciones-specimen)

**Especificaciones `Specimen`**

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|dna|arreglo de secuencias de ADN validado|String[]|\["ATC", "ATC", "ATC"\]
|mutant|indicador de si es mutante|Boolean|true

**Ejemplo**
```
{
    "requestId": "A168DB26-4C1B-4A38-9635-4990192B5135",
    "datetime": "2021-06-08T01:35:58.638+00:00",
    "status": 200,
    "message": "OK",
    "specimen": {
        "dna": [
            "AAAA",
            "TTTT",
            "CCCC",
            "GGGG"
        ],
        "mutant": true
    }
}
```


### Respuesta Exitosa No Mutante

##### Estatus HTTP Code
- 403 - OK

##### Cabeceras
```
Content-Type: application/json
X-REQUEST-ID: <Id de pa petición>
```

##### Cuerpo

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|requestId|id de la petición|String|ABCB75B3-53B9-4AB2-80FA-C31CB2C07006
|datetime|fecha y hora de la petición|Date|2021-06-08T01:35:58.638+00:00
|status|Código del estado de la respuesta|Integer|403
|message|Mensaje descriptivo de la respuesta|String|FORBIDDEN
|specimen|Objeto representativo de un Specimen|Object|Ver -> [Especificaciones Specimen](#especificaciones-specimen)

**Ejemplo**
```
{
    "requestId": "ABCB75B3-53B9-4AB2-80FA-C31CB2C07006",
    "datetime": "2021-06-08T01:32:59.141+00:00",
    "status": 403,
    "message": "FORBIDDEN",
    "specimen": {
        "dna": [
            "ATC",
            "ATC",
            "ATC"
        ],
        "mutant": false
    }
}
```


### Respuesta Fallida

##### Estatus HTTP Code
- 400 - BAD REQUEST
- 500 - SERVER INTERNAL ERROR

##### Cabeceras
```
Content-Type: application/json
X-REQUEST-ID: <Id de pa petición>
```

##### Cuerpo

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|requestId|id de la petición|String|6D75DA8E-6A53-4C8D-8CFB-A01272CD3967
|datetime|fecha y hora de la petición|Date|2021-06-08T02:24:09.974+00:00
|status|Código del estado de la respuesta|Integer|400
|message|Mensaje descriptivo de la respuesta|String|Invalid Content to sequence \[1, 1, 1, 1, 5, 6\], only A,T,C,G values are allowed

**Ejemplo** Contenido del ADN inválido
```
{
    "requestId": "6D75DA8E-6A53-4C8D-8CFB-A01272CD3967",
    "datetime": "2021-06-08T02:24:09.974+00:00",
    "status": 400,
    "message": "Invalid Content to sequence [1, 1, 1, 1, 5, 6], only A,T,C,G values are allowed"
}
```

**Ejemplo** Estructura del ADN inválido
```
{
    "requestId": "229A0AA6-05CD-4736-BFBC-40A80797581E",
    "datetime": "2021-06-08T02:27:06.764+00:00",
    "status": 400,
    "message": "Invalid Nucleotides info to sequence [A, T, G], the length of the DNA sequence must be equal to the sequence size,sequence size: 3, expected 2"
}
```

**Ejemplo** Cuerpo vacío
```
{
    "requestId": "738D819F-DDD9-4BF2-AF98-C80AA979E643",
    "datetime": "2021-06-08T02:58:43.409+00:00",
    "status": 500,
    "message": "Content type '' not supported"
}
```

**Ejemplo** DNA nulo
```
{
    "requestId": "79C46634-B80D-4688-AF16-6C8296FCB365",
    "datetime": "2021-06-08T03:00:49.089+00:00",
    "status": 400,
    "message": "Dna should be null"
}
```


### Servicio Stats
Servicio para obtener las estadísticas de la aplicación


#### URL:
- http://{server}:{port}/stats

#### Petición

##### Método Http
- GET


##### Ejemplo de una petición con curl
```
curl --location --request GET 'http://127.0.0.1:80/stats'
```


### Respuesta Exitosa

##### Estatus HTTP Code
- 200 - OK

##### Cabeceras
```
Content-Type: application/json
X-REQUEST-ID: <Id de pa petición>
```

##### Cuerpo

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|requestId|id de la petición|String|A168DB26-4C1B-4A38-9635-4990192B5135
|datetime|fecha y hora de la petición|Date|2021-06-08T01:35:58.638+00:00
|status|Código del estado de la respuesta|Integer|200
|message|Mensaje descriptivo de la respuesta|String|OK
|specimen|Objeto representativo de un Specimen|Object|Ver -> [Especificaciones Specimen](#especificaciones-specimen)

**Especificaciones `Specimen`**

|atributo|descripción|tipo|ejemplo|
|---|---|---|---|
|ratio|Promedio de peticiones (mutantes/humanos)|Double|2.0
|count_mutant_dna|Cantidad de mutantes detectados|Integer|4
|count_human_dna|Cantidad de Humanos detectados|Integer|2

**Ejemplo**
```
{
    "ratio": 2.0,
    "count_mutant_dna": 4,
    "count_human_dna": 2
}
```


---

### Error General 

##### **Ejemplo Petición a una URL no existente**

##### Estatus HTTP Code
- 404 - NOT FOUND

##### Cabeceras
```
Content-Type: application/json
X-REQUEST-ID: <Id de pa petición>
```

**Cuerpo**
```
{
    "timestamp": "2021-06-08T03:04:51.908+00:00",
    "status": 404,
    "error": "Not Found",
    "message": "",
    "path": "/stats2"
}
```