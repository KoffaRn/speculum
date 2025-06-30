"use client";

import { useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

export function Login({ onLogin }: { onLogin: (token: string) => void }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleLogin = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
      });

      if (!res.ok) throw new Error("Login failed");

      const data = await res.json();
      const token = data.token;
      localStorage.setItem("jwt", token);
      onLogin(token);
    } catch (err) {
      setError("Invalid credentials");
    }
  };

  return (
    <Card className="max-w-sm mx-auto mt-10">
      <CardContent className="p-6 space-y-4">
        <h2 className="text-xl font-bold text-center">Login</h2>
        <Input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <Input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        {error && <p className="text-red-500 text-sm text-center">{error}</p>}
        <Button className="w-full" onClick={handleLogin}>Login</Button>
      </CardContent>
    </Card>
  );
}