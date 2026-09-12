/** Legacy flat pools — prefer FloorPools for multilevel runs. */
export {
  Floor0CreaturePool as DefaultCreaturePool,
  Floor4BossPool as BossCreaturePool,
  Floor0CreaturePool,
  Floor1CreaturePool,
  Floor2CreaturePool,
  Floor3CreaturePool,
  Floor4CreaturePool,
  Floor0BossPool,
  Floor1BossPool,
  Floor2BossPool,
  Floor3BossPool,
  Floor4BossPool,
  creaturePoolForFloor,
  bossPoolForFloor,
} from './FloorPools';
