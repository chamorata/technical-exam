package utils

import groovy.json.JsonSlurper

class JsonDataReader {
    private Map testData

    JsonDataReader() {
        // GET ENVIRONMENT FROM SYSTEM PROPERTY; DEFAULT TO SIT IF NONE PROVIDED
        def envProp = (System.getProperty("env") ?: "SIT").toString()
        def env = envProp.trim().toUpperCase()

        // BUILD RESOURCE PATH BASED ON ENVIRONMENT
        def resourcePath = "data/${env}.json"
        println "JsonDataReader: looking for resource: ${resourcePath}"

        // FIND THE JSON FILE IN CLASSPATH
        URL res = this.class.classLoader.getResource(resourcePath)
        if (res == null) {
            // IF UPPERCASE FILENAME NOT FOUND, TRY LOWERCASE VERSION
            res = this.class.classLoader.getResource("data/${env.toLowerCase()}.json")
        }
        if (res == null) {
            throw new FileNotFoundException("Test data resource not found on classpath: ${resourcePath}")
        }

        // PARSE JSON FILE USING JsonSlurper
        def sl = new JsonSlurper()
        this.testData = sl.parse(res.openStream())
        if (!this.testData) {
            throw new IllegalStateException("Failed to parse JSON data from ${res}")
        }
        println "JsonDataReader: loaded data for ${env}"
    }
    Map getData() {
        return testData
    }
}