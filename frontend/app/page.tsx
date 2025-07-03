"use client";

import { useEffect, useState } from "react";
import { Clock } from "./components/widgets/Clock";
import { WeatherWidget } from "./components/widgets/WeatherWidget";
import { TransportWidget } from "./components/widgets/TransportWidget";
import { Login } from "./components/widgets/Login";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@radix-ui/react-dropdown-menu";
import { Rnd } from "react-rnd";

export default function Home() {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    const savedToken = localStorage.getItem("jwt");
    if (savedToken) setToken(savedToken);
  }, []);

  if (!token) return <Login onLogin={setToken} />;

  return (
    <main>
      <div className="fixed top-0 right-0 p-4 z-index-50">
      <DropdownMenu>
  <DropdownMenuTrigger className="bg-blue-500 text-white p-2 rounded">
    Menu
  </DropdownMenuTrigger>
  <DropdownMenuContent 
    side="bottom" 
    align="end" 
    className="bg-white shadow-lg text-black rounded p-2"
  >
    <DropdownMenuItem onSelect={() => console.log("Profile")}>
      Profile
    </DropdownMenuItem>
    <DropdownMenuItem onSelect={() => console.log("Settings")}>
      Settings
    </DropdownMenuItem>
    <DropdownMenuItem
      onSelect={() => {
        localStorage.removeItem("jwt");
        setToken(null);
      }}
    >
      Logout
    </DropdownMenuItem>
  </DropdownMenuContent>
</DropdownMenu>
      </div>
  <Rnd
    default={{ x: 0, y: 0, width: 300, height: 200 }}
    bounds="parent"
    className="bg-white rounded shadow p-4"
  >
    <Clock />
  </Rnd>

  <Rnd
    default={{ x: 320, y: 0, width: 300, height: 200 }}
    bounds="parent"
    className="bg-white rounded shadow p-4"
  >
    <WeatherWidget token={token} />
  </Rnd>

  <Rnd
    default={{ x: 640, y: 0, width: 300, height: 200 }}
    bounds="parent"
    className="bg-white rounded shadow p-4"
  >
    <TransportWidget token={token} />
  </Rnd>
    </main>
  );
}