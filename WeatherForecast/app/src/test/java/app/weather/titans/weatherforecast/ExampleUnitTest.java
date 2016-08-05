package app.weather.titans.weatherforecast;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.weather.titans.weatherforecast.helper.JSONWeatherParser;
import app.weather.titans.weatherforecast.model.DailyForecast;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /*@Test
    public void JSONParser_isCorrect() throws Exception {
        String cegled = "{\"city\":{\"id\":3054543,\"name\":\"Cegled\",\"coord\":{\"lon\":19.79952,\"lat\":47.172661},\"country\":\"HU\",\"population\":0,\"sys\":{\"population\":0}},\"cod\":\"200\",\"message\":0.0134,\"cnt\":40,\"list\":[{\"dt\":1470322800,\"main\":{\"temp\":303.77,\"temp_min\":300.927,\"temp_max\":303.77,\"pressure\":1016,\"sea_level\":1028.68,\"grnd_level\":1016,\"humidity\":76,\"temp_kf\":2.85},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.98,\"deg\":146.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-04 15:00:00\"},{\"dt\":1470333600,\"main\":{\"temp\":300.23,\"temp_min\":297.537,\"temp_max\":300.23,\"pressure\":1015.88,\"sea_level\":1028.61,\"grnd_level\":1015.88,\"humidity\":73,\"temp_kf\":2.69},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.97,\"deg\":106.002},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-04 18:00:00\"},{\"dt\":1470344400,\"main\":{\"temp\":296.27,\"temp_min\":293.734,\"temp_max\":296.27,\"pressure\":1015.67,\"sea_level\":1028.51,\"grnd_level\":1015.67,\"humidity\":83,\"temp_kf\":2.53},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.41,\"deg\":105.001},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-04 21:00:00\"},{\"dt\":1470355200,\"main\":{\"temp\":293.72,\"temp_min\":291.351,\"temp_max\":293.72,\"pressure\":1015.03,\"sea_level\":1027.88,\"grnd_level\":1015.03,\"humidity\":90,\"temp_kf\":2.37},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.41,\"deg\":97.5038},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-05 00:00:00\"},{\"dt\":1470366000,\"main\":{\"temp\":292.89,\"temp_min\":290.673,\"temp_max\":292.89,\"pressure\":1014.44,\"sea_level\":1027.25,\"grnd_level\":1014.44,\"humidity\":86,\"temp_kf\":2.21},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.42,\"deg\":94.0009},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-05 03:00:00\"},{\"dt\":1470376800,\"main\":{\"temp\":298.21,\"temp_min\":296.156,\"temp_max\":298.21,\"pressure\":1014.23,\"sea_level\":1027.08,\"grnd_level\":1014.23,\"humidity\":82,\"temp_kf\":2.06},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":2.61,\"deg\":122.503},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-05 06:00:00\"},{\"dt\":1470387600,\"main\":{\"temp\":302.08,\"temp_min\":300.183,\"temp_max\":302.08,\"pressure\":1014.05,\"sea_level\":1026.74,\"grnd_level\":1014.05,\"humidity\":76,\"temp_kf\":1.9},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.16,\"deg\":155.503},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-05 09:00:00\"},{\"dt\":1470398400,\"main\":{\"temp\":303.93,\"temp_min\":302.185,\"temp_max\":303.93,\"pressure\":1013.44,\"sea_level\":1026.06,\"grnd_level\":1013.44,\"humidity\":70,\"temp_kf\":1.74},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.42,\"deg\":154.005},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-05 12:00:00\"},{\"dt\":1470409200,\"main\":{\"temp\":304.01,\"temp_min\":302.423,\"temp_max\":304.01,\"pressure\":1012.49,\"sea_level\":1025.12,\"grnd_level\":1012.49,\"humidity\":67,\"temp_kf\":1.58},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.11,\"deg\":152.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-05 15:00:00\"},{\"dt\":1470420000,\"main\":{\"temp\":299.66,\"temp_min\":298.232,\"temp_max\":299.66,\"pressure\":1012.65,\"sea_level\":1025.64,\"grnd_level\":1012.65,\"humidity\":79,\"temp_kf\":1.42},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":32},\"wind\":{\"speed\":3.52,\"deg\":161.501},\"rain\":{\"3h\":0.76},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-05 18:00:00\"},{\"dt\":1470430800,\"main\":{\"temp\":295.27,\"temp_min\":294.001,\"temp_max\":295.27,\"pressure\":1014.65,\"sea_level\":1027.53,\"grnd_level\":1014.65,\"humidity\":89,\"temp_kf\":1.27},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":3.9,\"deg\":324.501},\"rain\":{\"3h\":1.81},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-05 21:00:00\"},{\"dt\":1470441600,\"main\":{\"temp\":293.65,\"temp_min\":292.545,\"temp_max\":293.65,\"pressure\":1015.31,\"sea_level\":1028.22,\"grnd_level\":1015.31,\"humidity\":90,\"temp_kf\":1.11},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":4.75,\"deg\":307.002},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-06 00:00:00\"},{\"dt\":1470452400,\"main\":{\"temp\":292.46,\"temp_min\":291.514,\"temp_max\":292.46,\"pressure\":1015.6,\"sea_level\":1028.52,\"grnd_level\":1015.6,\"humidity\":89,\"temp_kf\":0.95},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":4.42,\"deg\":301.502},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-06 03:00:00\"},{\"dt\":1470463200,\"main\":{\"temp\":294.35,\"temp_min\":293.558,\"temp_max\":294.35,\"pressure\":1016.49,\"sea_level\":1029.44,\"grnd_level\":1016.49,\"humidity\":89,\"temp_kf\":0.79},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":4.15,\"deg\":311.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-06 06:00:00\"},{\"dt\":1470474000,\"main\":{\"temp\":296.29,\"temp_min\":295.655,\"temp_max\":296.29,\"pressure\":1017.98,\"sea_level\":1030.82,\"grnd_level\":1017.98,\"humidity\":91,\"temp_kf\":0.63},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":2.31,\"deg\":26.5019},\"rain\":{\"3h\":0.13},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-06 09:00:00\"},{\"dt\":1470484800,\"main\":{\"temp\":296.02,\"temp_min\":295.547,\"temp_max\":296.02,\"pressure\":1018.37,\"sea_level\":1031.13,\"grnd_level\":1018.37,\"humidity\":93,\"temp_kf\":0.47},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":2.57,\"deg\":329},\"rain\":{\"3h\":0.57},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-06 12:00:00\"},{\"dt\":1470495600,\"main\":{\"temp\":295.25,\"temp_min\":294.936,\"temp_max\":295.25,\"pressure\":1018.43,\"sea_level\":1031.26,\"grnd_level\":1018.43,\"humidity\":92,\"temp_kf\":0.32},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.3,\"deg\":337.507},\"rain\":{\"3h\":0.28},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-06 15:00:00\"},{\"dt\":1470506400,\"main\":{\"temp\":293.71,\"temp_min\":293.549,\"temp_max\":293.71,\"pressure\":1019.47,\"sea_level\":1032.4,\"grnd_level\":1019.47,\"humidity\":91,\"temp_kf\":0.16},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":4.66,\"deg\":344.002},\"rain\":{\"3h\":0.26},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-06 18:00:00\"},{\"dt\":1470517200,\"main\":{\"temp\":292.156,\"temp_min\":292.156,\"temp_max\":292.156,\"pressure\":1021,\"sea_level\":1033.88,\"grnd_level\":1021,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.32,\"deg\":352.504},\"rain\":{\"3h\":1.13},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-06 21:00:00\"},{\"dt\":1470528000,\"main\":{\"temp\":291.24,\"temp_min\":291.24,\"temp_max\":291.24,\"pressure\":1021.88,\"sea_level\":1034.83,\"grnd_level\":1021.88,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":2.92,\"deg\":355.002},\"rain\":{\"3h\":0.43},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-07 00:00:00\"},{\"dt\":1470538800,\"main\":{\"temp\":290.531,\"temp_min\":290.531,\"temp_max\":290.531,\"pressure\":1022.16,\"sea_level\":1035.23,\"grnd_level\":1022.16,\"humidity\":93,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":2.77,\"deg\":324.504},\"rain\":{\"3h\":0.01},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-07 03:00:00\"},{\"dt\":1470549600,\"main\":{\"temp\":291.989,\"temp_min\":291.989,\"temp_max\":291.989,\"pressure\":1023.07,\"sea_level\":1036.07,\"grnd_level\":1023.07,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.72,\"deg\":340.501},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-07 06:00:00\"},{\"dt\":1470560400,\"main\":{\"temp\":295.693,\"temp_min\":295.693,\"temp_max\":295.693,\"pressure\":1024.09,\"sea_level\":1036.99,\"grnd_level\":1024.09,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":2.66,\"deg\":355.502},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-07 09:00:00\"},{\"dt\":1470571200,\"main\":{\"temp\":295.822,\"temp_min\":295.822,\"temp_max\":295.822,\"pressure\":1024.31,\"sea_level\":1037.11,\"grnd_level\":1024.31,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":36},\"wind\":{\"speed\":3.62,\"deg\":344.011},\"rain\":{\"3h\":0.08},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-07 12:00:00\"},{\"dt\":1470582000,\"main\":{\"temp\":296.188,\"temp_min\":296.188,\"temp_max\":296.188,\"pressure\":1023.88,\"sea_level\":1036.75,\"grnd_level\":1023.88,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":3.76,\"deg\":355.503},\"rain\":{\"3h\":0.06},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-07 15:00:00\"},{\"dt\":1470592800,\"main\":{\"temp\":292.783,\"temp_min\":292.783,\"temp_max\":292.783,\"pressure\":1024.52,\"sea_level\":1037.57,\"grnd_level\":1024.52,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":2.07,\"deg\":4.00037},\"rain\":{\"3h\":0.03},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-07 18:00:00\"},{\"dt\":1470603600,\"main\":{\"temp\":290.079,\"temp_min\":290.079,\"temp_max\":290.079,\"pressure\":1025.78,\"sea_level\":1038.7,\"grnd_level\":1025.78,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":48},\"wind\":{\"speed\":1.93,\"deg\":326.002},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-07 21:00:00\"},{\"dt\":1470614400,\"main\":{\"temp\":288.619,\"temp_min\":288.619,\"temp_max\":288.619,\"pressure\":1026.5,\"sea_level\":1039.48,\"grnd_level\":1026.5,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":1.81,\"deg\":49.0006},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-08 00:00:00\"},{\"dt\":1470625200,\"main\":{\"temp\":286.888,\"temp_min\":286.888,\"temp_max\":286.888,\"pressure\":1026.43,\"sea_level\":1039.55,\"grnd_level\":1026.43,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":2.56,\"deg\":55.5052},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-08 03:00:00\"},{\"dt\":1470636000,\"main\":{\"temp\":289.629,\"temp_min\":289.629,\"temp_max\":289.629,\"pressure\":1026.81,\"sea_level\":1039.84,\"grnd_level\":1026.81,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.92,\"deg\":41.5038},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-08 06:00:00\"},{\"dt\":1470646800,\"main\":{\"temp\":293.373,\"temp_min\":293.373,\"temp_max\":293.373,\"pressure\":1026.94,\"sea_level\":1039.79,\"grnd_level\":1026.94,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.5,\"deg\":18.5038},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-08 09:00:00\"},{\"dt\":1470657600,\"main\":{\"temp\":295.6,\"temp_min\":295.6,\"temp_max\":295.6,\"pressure\":1025.74,\"sea_level\":1038.59,\"grnd_level\":1025.74,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.11,\"deg\":294.001},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-08 12:00:00\"},{\"dt\":1470668400,\"main\":{\"temp\":296.707,\"temp_min\":296.707,\"temp_max\":296.707,\"pressure\":1024.45,\"sea_level\":1037.28,\"grnd_level\":1024.45,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.07,\"deg\":311},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-08 15:00:00\"},{\"dt\":1470679200,\"main\":{\"temp\":293.055,\"temp_min\":293.055,\"temp_max\":293.055,\"pressure\":1023.92,\"sea_level\":1036.92,\"grnd_level\":1023.92,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.26,\"deg\":301.002},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-08 18:00:00\"},{\"dt\":1470690000,\"main\":{\"temp\":288.773,\"temp_min\":288.773,\"temp_max\":288.773,\"pressure\":1024.44,\"sea_level\":1037.52,\"grnd_level\":1024.44,\"humidity\":82,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.36,\"deg\":164.001},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-08 21:00:00\"},{\"dt\":1470700800,\"main\":{\"temp\":287.684,\"temp_min\":287.684,\"temp_max\":287.684,\"pressure\":1024.39,\"sea_level\":1037.46,\"grnd_level\":1024.39,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.22,\"deg\":198.004},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-09 00:00:00\"},{\"dt\":1470711600,\"main\":{\"temp\":286.166,\"temp_min\":286.166,\"temp_max\":286.166,\"pressure\":1023.91,\"sea_level\":1037,\"grnd_level\":1023.91,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.07,\"deg\":207.024},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-08-09 03:00:00\"},{\"dt\":1470722400,\"main\":{\"temp\":292.842,\"temp_min\":292.842,\"temp_max\":292.842,\"pressure\":1024.27,\"sea_level\":1037.17,\"grnd_level\":1024.27,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":1.66,\"deg\":163.006},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-09 06:00:00\"},{\"dt\":1470733200,\"main\":{\"temp\":297.003,\"temp_min\":297.003,\"temp_max\":297.003,\"pressure\":1024.15,\"sea_level\":1037,\"grnd_level\":1024.15,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":20},\"wind\":{\"speed\":2.02,\"deg\":119.5},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-09 09:00:00\"},{\"dt\":1470744000,\"main\":{\"temp\":298.824,\"temp_min\":298.824,\"temp_max\":298.824,\"pressure\":1022.84,\"sea_level\":1035.72,\"grnd_level\":1022.84,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02d\"}],\"clouds\":{\"all\":8},\"wind\":{\"speed\":2.13,\"deg\":143.5},\"rain\":{},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2016-08-09 12:00:00\"}]}";

        long base = 1470376800;
        long date = base * 1000;
        DailyForecast dailyCegled = new DailyForecast(JSONWeatherParser.getWeather(cegled), date);

        System.out.println(dailyCegled.getmDate());

        System.out.println("descriptionNight: " + dailyCegled.getmDescriptionNight());
        System.out.println("iconCode:" + dailyCegled.getmIconCodeNight());
        System.out.println("temperatureNight: " + dailyCegled.getmTemperatureNight());

        System.out.println("descriptionDay:" + dailyCegled.getmDescriptionDay());
        System.out.println("iconCode: " + dailyCegled.getmIconCodeDay());
        System.out.println("temperatureDay: " + dailyCegled.getmTemperatureDay());



        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(date);
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    }*/

}