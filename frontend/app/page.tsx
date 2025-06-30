"use client";

import { useEffect, useState } from "react";
import { Clock } from "./components/widgets/Clock";
import { WeatherWidget } from "./components/widgets/WeatherWidget";
import { TransportWidget } from "./components/widgets/TransportWidget";
import { Login } from "./components/widgets/Login";

export default function Home() {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const savedToken = localStorage.getItem("jwt");
    if (savedToken) setToken(savedToken);
  }, []);

  if (!token) return <Login onLogin={setToken} />;

  return (
    <main className="grid gap-6 p-6 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
      <Clock />
      <WeatherWidget token={token} />
      <TransportWidget token={token} />
    </main>
  );
}