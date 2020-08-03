package com.beeline.forecast

class TestModels {

    fun groupWeatherString() : String = "{\"cnt\":3,\"list\":[{\"coord\":{\"lon\":37.62,\"lat\":55.75},\"sys\":{\"type\":1,\"id\":7323,\"message\":0.0036,\"country\":\"RU\",\"sunrise\":1485753940,\"sunset\":1485784855},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"main\":{\"temp\":-10.5,\"pressure\":1028,\"humidity\":66,\"temp_min\":-11,\"temp_max\":-10},\"visibility\":10000,\"wind\":{\"speed\":5,\"deg\":200},\"clouds\":{\"all\":0},\"dt\":1485793175,\"id\":524901,\"name\":\"Moscow\"},{\"coord\":{\"lon\":30.52,\"lat\":50.45},\"sys\":{\"type\":1,\"id\":7358,\"message\":0.0268,\"country\":\"UA\",\"sunrise\":1485754480,\"sunset\":1485787716},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"main\":{\"temp\":-11.04,\"pressure\":1033,\"humidity\":61,\"temp_min\":-15,\"temp_max\":-9},\"visibility\":10000,\"wind\":{\"speed\":3,\"deg\":150},\"clouds\":{\"all\":0},\"dt\":1485793175,\"id\":703448,\"name\":\"Kiev\"},{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0034,\"country\":\"GB\",\"sunrise\":1485762036,\"sunset\":1485794875},\"weather\":[{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\",\"icon\":\"50d\"},{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"main\":{\"temp\":7,\"pressure\":1012,\"humidity\":81,\"temp_min\":5,\"temp_max\":8},\"visibility\":10000,\"wind\":{\"speed\":4.6,\"deg\":90},\"clouds\":{\"all\":90},\"dt\":1485793175,\"id\":2643743,\"name\":\"London\"}]}"

    fun weatherString() : String = "{\n" +
            "  \"coord\": {\n" +
            "    \"lon\": -122.08,\n" +
            "    \"lat\": 37.39\n" +
            "  },\n" +
            "  \"weather\": [\n" +
            "    {\n" +
            "      \"id\": 800,\n" +
            "      \"main\": \"Clear\",\n" +
            "      \"description\": \"clear sky\",\n" +
            "      \"icon\": \"01d\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"base\": \"stations\",\n" +
            "  \"main\": {\n" +
            "    \"temp\": 282.55,\n" +
            "    \"feels_like\": 281.86,\n" +
            "    \"temp_min\": 280.37,\n" +
            "    \"temp_max\": 284.26,\n" +
            "    \"pressure\": 1023,\n" +
            "    \"humidity\": 100\n" +
            "  },\n" +
            "  \"visibility\": 16093,\n" +
            "  \"wind\": {\n" +
            "    \"speed\": 1.5,\n" +
            "    \"deg\": 350\n" +
            "  },\n" +
            "  \"clouds\": {\n" +
            "    \"all\": 1\n" +
            "  },\n" +
            "  \"dt\": 1560350645,\n" +
            "  \"sys\": {\n" +
            "    \"type\": 1,\n" +
            "    \"id\": 5122,\n" +
            "    \"message\": 0.0139,\n" +
            "    \"country\": \"US\",\n" +
            "    \"sunrise\": 1560343627,\n" +
            "    \"sunset\": 1560396563\n" +
            "  },\n" +
            "  \"timezone\": -25200,\n" +
            "  \"id\": 420006353,\n" +
            "  \"name\": \"Mountain View\",\n" +
            "  \"cod\": 200\n" +
            "}"

}