import { Team, Player, StandingsResponse } from '@/types';

// Schimbă cu IP-ul mașinii tale dacă rulezi pe dispozitiv fizic
// Pentru Android Emulator: http://10.0.2.2:8080
// Pentru Web: http://localhost:8080
const API_BASE_URL = 'http://192.168.100.34:8080/api';

async function fetchJson<T>(endpoint: string): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${endpoint}`);
  if (!response.ok) {
    throw new Error(`API Error: ${response.status} ${response.statusText}`);
  }
  return response.json();
}

/** Returnează clasamentele grupate pe conferințe (East / West). */
export async function getStandings(): Promise<StandingsResponse> {
  return fetchJson<StandingsResponse>('/teams/standings');
}

/** Returnează toate echipele. */
export async function getTeams(): Promise<Team[]> {
  return fetchJson<Team[]>('/teams');
}

/** Returnează o echipă după ID. */
export async function getTeamById(id: number): Promise<Team> {
  return fetchJson<Team>(`/teams/${id}`);
}

/** Returnează jucătorii unei echipe. */
export async function getPlayersByTeam(teamId: number): Promise<Player[]> {
  return fetchJson<Player[]>(`/players/team/${teamId}`);
}

/** Caută jucători după nume. */
export async function searchPlayers(query: string): Promise<Player[]> {
  return fetchJson<Player[]>(`/players/search?query=${encodeURIComponent(query)}`);
}
