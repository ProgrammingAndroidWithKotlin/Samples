package com.peterlaurence.book.javatokotlin.cehandcancellation

data class Hike(val name: String, val miles: Float, val accentInFeet: Int)
class Weather
data class HikeData(val hike: Hike, val weather: Weather?)

fun fetchHikesForUser(userId: String): List<Hike> {
    // queries a remote server
    Thread.sleep(150)

    // simulate a list of hikes
    val hike1 = Hike("Cascade Creek", 3.7f, 452)
    val hike2 = Hike("Wapiti Lake", 31.3f, 3106)
    val hike3 = Hike("Mystic Falls", 2.4f, 550)
    val hike4 = Hike("Upper Geyser", 4.5f, 242)
    return listOf(hike1, hike2, hike3, hike4)
}

fun fetchWeather(hike: Hike): Weather {
    // queries a remote server
    Thread.sleep(150)
    return Weather()
}

