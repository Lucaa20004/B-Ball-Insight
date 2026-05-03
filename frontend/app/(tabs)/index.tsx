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
import { getStandings } from '@/services/api';
import { Team } from '@/types';

type Conference = 'East' | 'West';

export default function StandingsScreen() {
  const [standings, setStandings] = useState<{ East: Team[]; West: Team[] }>({ East: [], West: [] });
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [activeConf, setActiveConf] = useState<Conference>('East');
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    try {
      setError(null);
      const data = await getStandings();
      setStandings(data);
    } catch (e: any) {
      setError('Nu s-au putut încărca clasamentele. Verifică backend-ul.');
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => { fetchData(); }, []);

  const onRefresh = () => { setRefreshing(true); fetchData(); };

  const teams = activeConf === 'East' ? standings.East : standings.West;

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#E85D2C" />
        <Text style={styles.loadingText}>Se încarcă clasamentele...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Text style={styles.title}>🏀 Clasamente NBA</Text>
        <Text style={styles.subtitle}>Sezonul 2024-2025</Text>
      </View>

      {/* Conference Toggle */}
      <View style={styles.toggleRow}>
        {(['East', 'West'] as Conference[]).map((conf) => (
          <TouchableOpacity
            key={conf}
            style={[styles.toggleBtn, activeConf === conf && styles.toggleBtnActive]}
            onPress={() => setActiveConf(conf)}
          >
            <Text style={[styles.toggleText, activeConf === conf && styles.toggleTextActive]}>
              {conf === 'East' ? '🔵 Eastern' : '🔴 Western'}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      {error ? (
        <View style={styles.errorBox}>
          <Text style={styles.errorText}>{error}</Text>
          <TouchableOpacity style={styles.retryBtn} onPress={fetchData}>
            <Text style={styles.retryText}>Reîncearcă</Text>
          </TouchableOpacity>
        </View>
      ) : (
        <ScrollView
          style={styles.tableScroll}
          refreshControl={<RefreshControl refreshing={refreshing} onRefresh={onRefresh} tintColor="#E85D2C" />}
        >
          {/* Table Header */}
          <View style={styles.tableHeader}>
            <Text style={[styles.thCell, styles.rankCol]}>#</Text>
            <Text style={[styles.thCell, styles.teamCol]}>Echipă</Text>
            <Text style={[styles.thCell, styles.statCol]}>W</Text>
            <Text style={[styles.thCell, styles.statCol]}>L</Text>
            <Text style={[styles.thCell, styles.pctCol]}>PCT</Text>
            <Text style={[styles.thCell, styles.recCol]}>Home</Text>
            <Text style={[styles.thCell, styles.recCol]}>Road</Text>
          </View>

          {/* Rows */}
          {teams.map((team, idx) => {
            const pct = team.wins + team.losses > 0
              ? (team.wins / (team.wins + team.losses)).toFixed(3).replace('0.', '.')
              : '.000';
            const isPlayoff = (team.conferenceRank ?? idx + 1) <= 10;

            return (
              <View
                key={team.id}
                style={[
                  styles.tableRow,
                  idx % 2 === 0 ? styles.rowEven : styles.rowOdd,
                  isPlayoff && styles.rowPlayoff,
                ]}
              >
                <Text style={[styles.tdCell, styles.rankCol, styles.rankText]}>
                  {team.conferenceRank ?? idx + 1}
                </Text>
                <View style={[styles.teamCol, styles.teamInfo]}>
                  <Text style={styles.teamAbbr}>{team.abbreviation}</Text>
                  <Text style={styles.teamName} numberOfLines={1}>{team.city}</Text>
                </View>
                <Text style={[styles.tdCell, styles.statCol, styles.winsText]}>{team.wins}</Text>
                <Text style={[styles.tdCell, styles.statCol, styles.lossText]}>{team.losses}</Text>
                <Text style={[styles.tdCell, styles.pctCol]}>{pct}</Text>
                <Text style={[styles.tdCell, styles.recCol]}>{team.homeRecord ?? '-'}</Text>
                <Text style={[styles.tdCell, styles.recCol]}>{team.roadRecord ?? '-'}</Text>
              </View>
            );
          })}

          {/* Legend */}
          <View style={styles.legend}>
            <View style={[styles.legendDot, { backgroundColor: 'rgba(232,93,44,0.15)' }]} />
            <Text style={styles.legendText}>Playoff (Top 10)</Text>
          </View>
        </ScrollView>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#0B1120' },
  center: { flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: '#0B1120' },
  loadingText: { color: '#8899AA', marginTop: 12, fontSize: 14 },

  header: { paddingTop: 60, paddingHorizontal: 20, paddingBottom: 16 },
  title: { color: '#FFFFFF', fontSize: 28, fontWeight: '800' },
  subtitle: { color: '#6B7D93', fontSize: 14, marginTop: 4 },

  toggleRow: { flexDirection: 'row', marginHorizontal: 16, marginBottom: 12, gap: 8 },
  toggleBtn: {
    flex: 1, paddingVertical: 10, borderRadius: 10,
    backgroundColor: 'rgba(255,255,255,0.05)', alignItems: 'center',
  },
  toggleBtnActive: { backgroundColor: 'rgba(232,93,44,0.2)', borderWidth: 1, borderColor: '#E85D2C' },
  toggleText: { color: '#6B7D93', fontSize: 14, fontWeight: '600' },
  toggleTextActive: { color: '#E85D2C' },

  errorBox: { alignItems: 'center', padding: 30 },
  errorText: { color: '#FF6B6B', fontSize: 14, textAlign: 'center', marginBottom: 12 },
  retryBtn: { backgroundColor: '#E85D2C', paddingHorizontal: 20, paddingVertical: 10, borderRadius: 8 },
  retryText: { color: '#FFF', fontWeight: '700' },

  tableScroll: { flex: 1, paddingHorizontal: 8 },
  tableHeader: {
    flexDirection: 'row', paddingVertical: 10, paddingHorizontal: 8,
    borderBottomWidth: 1, borderBottomColor: 'rgba(255,255,255,0.1)',
  },
  thCell: { color: '#6B7D93', fontSize: 11, fontWeight: '700', textTransform: 'uppercase' },

  tableRow: {
    flexDirection: 'row', paddingVertical: 12, paddingHorizontal: 8,
    alignItems: 'center', borderRadius: 6, marginVertical: 1,
  },
  rowEven: { backgroundColor: 'rgba(255,255,255,0.02)' },
  rowOdd: { backgroundColor: 'transparent' },
  rowPlayoff: { backgroundColor: 'rgba(232,93,44,0.06)' },

  tdCell: { color: '#D1D9E6', fontSize: 13 },

  rankCol: { width: 28, textAlign: 'center' },
  teamCol: { flex: 1, marginHorizontal: 4 },
  statCol: { width: 32, textAlign: 'center' },
  pctCol: { width: 42, textAlign: 'center' },
  recCol: { width: 50, textAlign: 'center' },

  rankText: { color: '#6B7D93', fontWeight: '700' },
  teamInfo: { flexDirection: 'row', alignItems: 'center', gap: 6 },
  teamAbbr: { color: '#E85D2C', fontSize: 13, fontWeight: '800', width: 36 },
  teamName: { color: '#D1D9E6', fontSize: 13, flex: 1 },
  winsText: { color: '#4ADE80', fontWeight: '600' },
  lossText: { color: '#F87171', fontWeight: '600' },

  legend: {
    flexDirection: 'row', alignItems: 'center', gap: 8,
    paddingVertical: 16, paddingHorizontal: 12,
  },
  legendDot: { width: 12, height: 12, borderRadius: 3 },
  legendText: { color: '#6B7D93', fontSize: 12 },
});
