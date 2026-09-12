import { Item } from '../Item';
import type { IconRef } from '../../utils/icons';

export type AccessoryKind =
  | 'shield'
  | 'wraps'
  | 'grips'
  | 'venomFlask'
  | 'pirateCoin'
  | 'ringOfFire'
  | 'ringOfIce'
  | 'ringOfElectricity'
  | 'ironBand'
  | 'scrapPouch'
  | 'lockpick';

export class Accessory extends Item {
  private kind: AccessoryKind;
  private description: string;

  constructor(name: string, kind: AccessoryKind, description: string, icon: IconRef = null) {
    super(name, 1, icon);
    this.kind = kind;
    this.description = description;
  }

  getKind(): AccessoryKind {
    return this.kind;
  }

  getDescription(): string {
    return this.description;
  }

  getToolTipText(): string {
    return `${this.name}: ${this.description}`;
  }
}
