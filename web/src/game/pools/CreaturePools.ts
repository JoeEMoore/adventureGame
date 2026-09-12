/** Legacy flat pools — prefer FloorPools for multilevel runs. */
export {
  Floor0CreaturePool as DefaultCreaturePool,
  Floor2BossPool as BossCreaturePool,
  Floor0CreaturePool,
  Floor1CreaturePool,
  Floor2CreaturePool,
  Floor0BossPool,
  Floor1BossPool,
  Floor2BossPool,
  creaturePoolForFloor,
  bossPoolForFloor,
} from './FloorPools';
