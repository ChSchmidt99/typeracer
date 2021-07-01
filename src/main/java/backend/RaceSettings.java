package backend;

class RaceSettings {

  // Durations in second
  final long checkeredFlagDuration;
  final long updateInterval;

  RaceSettings(long checkeredFlagDuration, long updateInterval) {
    this.checkeredFlagDuration = checkeredFlagDuration;
    this.updateInterval = updateInterval;
  }
}
