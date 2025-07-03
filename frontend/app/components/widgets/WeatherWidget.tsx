"use client";
import { Card, CardContent } from "@/components/ui/card";
import { useEffect, useState } from "react";


interface Forecast {
  currentTemp: number;
  dailyStats: {
    date: string;
    minTemp: number;
    maxTemp: number;
    totalPrecipitation: number;
    maxChanceOfRain: number;
  }[]
}
export function WeatherWidget({token }: { token: string }) {
  const [forecast, setForecast] = useState<Forecast | null>(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/weather/forecast?lat=57.7089&lon=11.9746",
      {headers: { Authorization: `Bearer ${token}` }}
    )
      .then((res) => res.json())
      .then((data: Forecast) => {
        if (!data || !data.dailyStats || data.dailyStats.length === 0) {
          setForecast(null);
          return;
        }
        setForecast(data);
      });
  }, []);

  return (
    <Card>
      <CardContent className="text-center p-6">
        <h2 className="text-xl font-bold">Weather</h2>
        <p className="text-4xl mt-4">Temperature: {forecast?.currentTemp ?? "Loading..."}</p>
        {(forecast?.dailyStats || []).map((day) => (
          <div className="mt-4" key={day.date}>
            <h3 className="text-lg font-semibold">{day.date}</h3>
            <p>Min: {day.minTemp}°C, Max: {day.maxTemp}°C</p>
            <p>Total Precipitation: {day.totalPrecipitation}mm</p>
            <p>Max Chance of Rain: {day.maxChanceOfRain}%</p>
          </div>
        ))}
      </CardContent>
    </Card>
  );
}