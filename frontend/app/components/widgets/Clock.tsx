"use client";

import { Card, CardContent } from "@/components/ui/card";
import { useEffect, useState } from "react";

export function Clock() {
  const [time, setTime] = useState<Date | null>(null);

  useEffect(() => {
    const update = () => setTime(new Date());
    update(); // initial call
    const interval = setInterval(update, 1000);
    return () => clearInterval(interval);
  }, []);

  if (!time) return null; // or a fallback like <p>Loading...</p>

  return (
    <Card>
      <CardContent className="text-center p-6">
        <h2 className="text-xl font-bold">Clock</h2>
        <p className="text-4xl mt-4">{time.toLocaleTimeString()}</p>
      </CardContent>
    </Card>
  );
}