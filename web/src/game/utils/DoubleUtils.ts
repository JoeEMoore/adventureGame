export function roundDouble(d: number, digitsAfterDecimal = 1): string {
  return d.toFixed(digitsAfterDecimal);
}
