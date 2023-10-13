package org.deafsapps.kpdesignpatterns.structural

/**
 * Adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate.
 *
 * Problem:
 * If we have a system which processes certain data coming from a data source and, at some point, another data
 * source is available but data is fetched in a different format.
 *
 * Solution:
 * The Adapter design pattern recommends to implement a new wrapper entity which abstract out the mapping required to
 * make any data compatible with the processor, regardless its origin.
 *
 * Android Example:
 * In classic Android development, it's pretty common to use adapters to plug data into UI widgets to display collections
 * (ListView, RecyclerView, etc.).
 */

fun main() {
    val kelvinThermometer: KelvinThermometerAdapter =
        KelvinThermometerAdapterImpl(kelvinThermometer = KelvinThermometer())

    val celsiusTemperature = Celsius(measure = 25f)
    val fahrenheitTemperature = Fahrenheit(measure = 25f)
    val kelvinTemperature = Kelvin(measure = 25f)

    kelvinThermometer.run {
        setTemperatureFromCelsius(temperature = celsiusTemperature)
        println(fetchTemperatureInfo())
        setTemperatureFromFahrenheit(temperature = fahrenheitTemperature)
        println(fetchTemperatureInfo())
        setTemperatureFromKelvin(temperature = kelvinTemperature)
        println(fetchTemperatureInfo())
    }
}

interface KelvinThermometerAdapter {
    val kelvinThermometer: KelvinThermometer

    fun setTemperatureFromKelvin(temperature: Kelvin)

    fun setTemperatureFromCelsius(temperature: Celsius)

    fun setTemperatureFromFahrenheit(temperature: Fahrenheit)

    fun fetchTemperatureInfo(): String
}

class KelvinThermometerAdapterImpl(override val kelvinThermometer: KelvinThermometer) : KelvinThermometerAdapter {

    override fun setTemperatureFromKelvin(temperature: Kelvin) {
        kelvinThermometer.temperature = temperature
    }

    override fun setTemperatureFromCelsius(temperature: Celsius) {
        val kelvinMeasure: Float = temperature.measure + 273.15f
        kelvinThermometer.temperature = Kelvin(measure = kelvinMeasure)
    }

    override fun setTemperatureFromFahrenheit(temperature: Fahrenheit) {
        val kelvinMeasure: Float = (temperature.measure - 32) * 5.0f / 9 + 273.15f
        kelvinThermometer.temperature = Kelvin(measure = kelvinMeasure)
    }

    override fun fetchTemperatureInfo(): String =
        "Current temperature is ${kelvinThermometer.temperature.measure} Kelvin degrees"
}

class KelvinThermometer(var temperature: Kelvin = Kelvin(measure = -1f))

@JvmInline
value class Kelvin(val measure: Float)

@JvmInline
value class Celsius(val measure: Float)

@JvmInline
value class Fahrenheit(val measure: Float)
