import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  ScrollView,
  StyleSheet,
  ActivityIndicator,
  TouchableOpacity,
  RefreshControl,
} from 'react-native';
import { getTeams, getPlayersByTeam } from '@/services/api';
import { Team, Player } from '@/types';

export default function RostersScreen() {
  const [teams, setTeams] = useState<Team[]>([]);
  const [selectedTeam, setSelectedTeam] = useState<Team | null>(null);
  const [players, setPlayers] = useState<Player[]>([]);
  const [loading, setLoading] = useState(true);
  const [loadingPlayers, setLoadingPlayers] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchTeams = async () => {
    try {
      setError(null);
      const data = await getTeams();
      setTeams(data);
      if (data.length > 0 && !selectedTeam) {
        selectTeam(data[0]);
      }
    } catch (e: any) {
      setError('Nu s-au putut încărca echipele.');
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  const selectTeam = async (team: Team) => {
    setSelectedTeam(team);
    setLoadingPlayers(true);
    try {
      const data = await getPlayersByTeam(team.id);
      setPlayers(data);
    } catch {
      setPlayers([]);
    } finally {
      setLoadingPlayers(false);
    }
  };

  useEffect(() => { fetchTeams(); }, []);

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#E85D2C" />
        <Text style={styles.loadingText}>Se încarcă echipele...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Text style={styles.title}>🏀 Loturi NBA</Text>
        <Text style={styles.subtitle}>Selectează o echipă</Text>
      </View>

      {error ? (
        <View style={styles.errorBox}>
          <Text style={styles.errorText}>{error}</Text>
          <TouchableOpacity style={styles.retryBtn} onPress={fetchTeams}>
            <Text style={styles.retryText}>Reîncearcă</Text>
          </TouchableOpacity>
        </View>
      ) : (
        <>
          {/* Team selector horizontal scroll */}
          <ScrollView
            horizontal
            showsHorizontalScrollIndicator={false}
            style={styles.teamScroll}
            contentContainerStyle={styles.teamScrollContent}
          >
            {teams.map((team) => (
              <TouchableOpacity
                key={team.id}
                style={[
                  styles.teamChip,
                  selectedTeam?.id === team.id && styles.teamChipActive,
                ]}
                onPress={() => selectTeam(team)}
              >
                <Text
                  style={[
                    styles.teamChipText,
                    selectedTeam?.id === team.id && styles.teamChipTextActive,
                  ]}
                >
                  {team.abbreviation}
                </Text>
              </TouchableOpacity>
            ))}
          </ScrollView>

          {/* Selected team info */}
          {selectedTeam && (
            <View style={styles.teamBanner}>
              <Text style={styles.bannerName}>{selectedTeam.fullName}</Text>
              <View style={styles.bannerStats}>
                <View style={styles.bannerStat}>
                  <Text style={styles.bannerStatValue}>{selectedTeam.wins ?? 0}</Text>
                  <Text style={styles.bannerStatLabel}>Wins</Text>
                </View>
                <View style={styles.bannerDivider} />
                <View style={styles.bannerStat}>
                  <Text style={styles.bannerStatValue}>{selectedTeam.losses ?? 0}</Text>
                  <Text style={styles.bannerStatLabel}>Losses</Text>
                </View>
                <View style={styles.bannerDivider} />
                <View style={styles.bannerStat}>
                  <Text style={styles.bannerStatValue}>{selectedTeam.conference}</Text>
                  <Text style={styles.bannerStatLabel}>Conf</Text>
                </View>
                <View style={styles.bannerDivider} />
                <View style={styles.bannerStat}>
                  <Text style={styles.bannerStatValue}>#{selectedTeam.conferenceRank ?? '-'}</Text>
                  <Text style={styles.bannerStatLabel}>Rank</Text>
                </View>
              </View>
            </View>
          )}

          {/* Player list */}
          {loadingPlayers ? (
            <View style={styles.center}>
              <ActivityIndicator size="small" color="#E85D2C" />
            </View>
          ) : (
            <ScrollView
              style={styles.playerList}
              refreshControl={
                <RefreshControl
                  refreshing={refreshing}
                  onRefresh={() => { setRefreshing(true); fetchTeams(); }}
                  tintColor="#E85D2C"
                />
              }
            >
              {/* Table header */}
              <View style={styles.playerHeader}>
                <Text style={[styles.phCell, styles.jerseyCol]}>#</Text>
                <Text style={[styles.phCell, styles.nameCol]}>Jucător</Text>
                <Text style={[styles.phCell, styles.posCol]}>Pos</Text>
                <Text style={[styles.phCell, styles.heightCol]}>Înălțime</Text>
                <Text style={[styles.phCell, styles.collegeCol]}>Universitate</Text>
              </View>

              {players.length === 0 ? (
                <View style={styles.emptyState}>
                  <Text style={styles.emptyText}>Nu sunt jucători disponibili.</Text>
                </View>
              ) : (
                players.map((player, idx) => (
                  <View
                    key={player.id}
                    style={[styles.playerRow, idx % 2 === 0 ? styles.rowEven : styles.rowOdd]}
                  >
                    <Text style={[styles.prCell, styles.jerseyCol, styles.jerseyText]}>
                      {player.jerseyNumber ?? '-'}
                    </Text>
                    <View style={[styles.nameCol]}>
                      <Text style={styles.playerName}>
                        {player.firstName} {player.lastName}
                      </Text>
                      <Text style={styles.playerCountry}>{player.country ?? ''}</Text>
                    </View>
                    <Text style={[styles.prCell, styles.posCol, styles.posText]}>
                      {player.position ?? '-'}
                    </Text>
                    <Text style={[styles.prCell, styles.heightCol]}>
                      {player.height ?? '-'}
                    </Text>
                    <Text style={[styles.prCell, styles.collegeCol]} numberOfLines={1}>
                      {player.college ?? '-'}
                    </Text>
                  </View>
                ))
              )}

              <View style={{ height: 40 }} />
            </ScrollView>
          )}
        </>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#0B1120' },
  center: { flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: '#0B1120' },
  loadingText: { color: '#8899AA', marginTop: 12, fontSize: 14 },

  header: { paddingTop: 60, paddingHorizontal: 20, paddingBottom: 12 },
  title: { color: '#FFFFFF', fontSize: 28, fontWeight: '800' },
  subtitle: { color: '#6B7D93', fontSize: 14, marginTop: 4 },

  errorBox: { alignItems: 'center', padding: 30 },
  errorText: { color: '#FF6B6B', fontSize: 14, textAlign: 'center', marginBottom: 12 },
  retryBtn: { backgroundColor: '#E85D2C', paddingHorizontal: 20, paddingVertical: 10, borderRadius: 8 },
  retryText: { color: '#FFF', fontWeight: '700' },

  teamScroll: { maxHeight: 52, marginBottom: 8 },
  teamScrollContent: { paddingHorizontal: 16, gap: 6, alignItems: 'center' },
  teamChip: {
    paddingHorizontal: 14, paddingVertical: 8, borderRadius: 20,
    backgroundColor: 'rgba(255,255,255,0.06)', borderWidth: 1, borderColor: 'transparent',
  },
  teamChipActive: { backgroundColor: 'rgba(232,93,44,0.2)', borderColor: '#E85D2C' },
  teamChipText: { color: '#6B7D93', fontSize: 13, fontWeight: '700' },
  teamChipTextActive: { color: '#E85D2C' },

  teamBanner: {
    marginHorizontal: 16, marginBottom: 12, padding: 16,
    backgroundColor: 'rgba(232,93,44,0.08)', borderRadius: 12,
    borderWidth: 1, borderColor: 'rgba(232,93,44,0.2)',
  },
  bannerName: { color: '#FFFFFF', fontSize: 18, fontWeight: '800', marginBottom: 12 },
  bannerStats: { flexDirection: 'row', alignItems: 'center' },
  bannerStat: { flex: 1, alignItems: 'center' },
  bannerStatValue: { color: '#FFFFFF', fontSize: 18, fontWeight: '700' },
  bannerStatLabel: { color: '#6B7D93', fontSize: 11, marginTop: 2, textTransform: 'uppercase' },
  bannerDivider: { width: 1, height: 30, backgroundColor: 'rgba(255,255,255,0.1)' },

  playerList: { flex: 1, paddingHorizontal: 8 },
  playerHeader: {
    flexDirection: 'row', paddingVertical: 8, paddingHorizontal: 8,
    borderBottomWidth: 1, borderBottomColor: 'rgba(255,255,255,0.1)',
  },
  phCell: { color: '#6B7D93', fontSize: 11, fontWeight: '700', textTransform: 'uppercase' },

  playerRow: {
    flexDirection: 'row', paddingVertical: 12, paddingHorizontal: 8,
    alignItems: 'center', borderRadius: 6, marginVertical: 1,
  },
  rowEven: { backgroundColor: 'rgba(255,255,255,0.02)' },
  rowOdd: { backgroundColor: 'transparent' },

  prCell: { color: '#D1D9E6', fontSize: 13 },

  jerseyCol: { width: 32, textAlign: 'center' },
  nameCol: { flex: 1, marginHorizontal: 4 },
  posCol: { width: 36, textAlign: 'center' },
  heightCol: { width: 52, textAlign: 'center' },
  collegeCol: { width: 80, textAlign: 'left' },

  jerseyText: { color: '#E85D2C', fontWeight: '800' },
  playerName: { color: '#FFFFFF', fontSize: 14, fontWeight: '600' },
  playerCountry: { color: '#6B7D93', fontSize: 11, marginTop: 1 },
  posText: { color: '#60A5FA', fontWeight: '600' },

  emptyState: { alignItems: 'center', paddingVertical: 40 },
  emptyText: { color: '#6B7D93', fontSize: 14 },
});
