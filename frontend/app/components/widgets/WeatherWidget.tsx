"use client";
import { Card, CardContent } from "@/components/ui/card";
import { useEffect, useState } from "react";

export function WeatherWidget({token }: { token: string }) {
  const [forecast, setForecast] = useState<string | null>(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/weather/forecast?lat=57.7089&lon=11.9746",
      {headers: { Authorization: `Bearer ${token}` }}
    )
      .then((res) => res.json())
      .then((data) => {
        const now = data.timeSeries?.[0];
        const tempParam = now?.parameters.find((p: { name: string; }) => p.name === "t")?.values[0];
        setForecast(`${tempParam}°C`);
      });
  }, []);

  return (
    <Card>
      <CardContent className="text-center p-6">
        <h2 className="text-xl font-bold">Weather</h2>
        <p className="text-4xl mt-4">{forecast ?? "Loading..."}</p>
      </CardContent>
    </Card>
  );
}