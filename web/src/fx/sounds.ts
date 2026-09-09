/** Lightweight Web Audio SFX — no external files required. */

let ctx: AudioContext | null = null;

function getCtx(): AudioContext | null {
  if (typeof window === 'undefined') return null;
  if (!ctx) {
    const AC = window.AudioContext || (window as unknown as { webkitAudioContext: typeof AudioContext }).webkitAudioContext;
    ctx = new AC();
  }
  if (ctx.state === 'suspended') {
    void ctx.resume();
  }
  return ctx;
}

function tone(
  freq: number,
  duration: number,
  type: OscillatorType = 'square',
  gain = 0.08,
  freqEnd?: number,
): void {
  const audio = getCtx();
  if (!audio) return;
  const t0 = audio.currentTime;
  const osc = audio.createOscillator();
  const g = audio.createGain();
  osc.type = type;
  osc.frequency.setValueAtTime(freq, t0);
  if (freqEnd !== undefined) {
    osc.frequency.exponentialRampToValueAtTime(Math.max(1, freqEnd), t0 + duration);
  }
  g.gain.setValueAtTime(gain, t0);
  g.gain.exponentialRampToValueAtTime(0.001, t0 + duration);
  osc.connect(g);
  g.connect(audio.destination);
  osc.start(t0);
  osc.stop(t0 + duration + 0.02);
}

function noiseBurst(duration: number, gain = 0.12): void {
  const audio = getCtx();
  if (!audio) return;
  const t0 = audio.currentTime;
  const bufferSize = Math.floor(audio.sampleRate * duration);
  const buffer = audio.createBuffer(1, bufferSize, audio.sampleRate);
  const data = buffer.getChannelData(0);
  for (let i = 0; i < bufferSize; i++) {
    data[i] = (Math.random() * 2 - 1) * (1 - i / bufferSize);
  }
  const src = audio.createBufferSource();
  src.buffer = buffer;
  const filter = audio.createBiquadFilter();
  filter.type = 'bandpass';
  filter.frequency.value = 900;
  filter.Q.value = 0.8;
  const g = audio.createGain();
  g.gain.setValueAtTime(gain, t0);
  g.gain.exponentialRampToValueAtTime(0.001, t0 + duration);
  src.connect(filter);
  filter.connect(g);
  g.connect(audio.destination);
  src.start(t0);
  src.stop(t0 + duration);
}

export const sfx = {
  doorEnter() {
    tone(180, 0.08, 'triangle', 0.06, 90);
    tone(120, 0.12, 'sine', 0.04, 60);
  },
  hit() {
    noiseBurst(0.09, 0.14);
    tone(220, 0.07, 'square', 0.05, 80);
  },
  miss() {
    tone(420, 0.12, 'triangle', 0.05, 160);
  },
  shopBuy() {
    tone(880, 0.06, 'sine', 0.06);
    tone(1175, 0.1, 'sine', 0.05);
  },
  bossSting() {
    tone(110, 0.25, 'sawtooth', 0.07, 55);
    tone(165, 0.35, 'square', 0.04, 80);
    tone(55, 0.4, 'triangle', 0.06);
  },
};
