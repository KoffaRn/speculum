"use client";
import { Card, CardContent } from "@/components/ui/card";
import { useEffect, useState } from "react";
import { formatTime } from "../../helper"; // Assuming you have a helper function to format time

interface Leg {
  travelMode: string;
  departureTime: string;
  arrivalTime: string;
  departurePlaceName: string;
  arrivalPlaceName: string;
}

export function TransportWidget({token }: { token: string }) {
  const [legs, setLegs] = useState<Leg[]>([]);

  useEffect(() => {
  fetch("http://localhost:8080/api/journeys/journey?fromLat=57.7072326&fromLon=11.9670171&toLat=57.7210839&toLon=12.9407407", {
    headers: { Authorization: `Bearer ${token}` }
  })
    .then((res) => res.json())
    .then((data) => {
      const legs = data || [];
      const formattedLegs = legs.map((leg: Leg) => ({
        ...leg,
        departureTime: formatTime(leg.departureTime),
        arrivalTime: formatTime(leg.arrivalTime),
      }));
      setLegs(formattedLegs);
    });
}, []);

  return (
    <Card>
      <CardContent className="p-4">
        <h2 className="text-xl font-bold mb-2">Next Departures</h2>
        <ul className="text-sm">
          {legs.map((leg, i) => (
            <li key={i} className="mb-2">
              <strong>{leg.travelMode}</strong> from {leg.departurePlaceName} to {leg.arrivalPlaceName}<br />
              {leg.departureTime} → {leg.arrivalTime}
            </li>
          ))}
        </ul>
      </CardContent>
    </Card>
  );
}
