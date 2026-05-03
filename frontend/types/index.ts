export interface Team {
  id: number;
  apiId: number;
  conference: string;
  division: string;
  city: string;
  name: string;
  fullName: string;
  abbreviation: string;
  wins: number;
  losses: number;
  conferenceRank: number;
  divisionRank: number;
  conferenceRecord: string;
  divisionRecord: string;
  homeRecord: string;
  roadRecord: string;
  season: number;
}

export interface Player {
  id: number;
  apiId: number;
  firstName: string;
  lastName: string;
  position: string;
  height: string;
  weight: string;
  jerseyNumber: string;
  college: string;
  country: string;
  draftYear: number;
  draftRound: number;
  draftNumber: number;
  team: Team;
}

export interface StandingsResponse {
  East: Team[];
  West: Team[];
}
