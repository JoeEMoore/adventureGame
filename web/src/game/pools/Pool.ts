export type ObjectCreator<T> = () => T;

export class Pool<T> {
  private creators = new Map<ObjectCreator<T>, number>();
  private totalWeight = 0;

  addObjectCreator(creator: ObjectCreator<T>, weight: number): void {
    const prev = this.creators.get(creator);
    if (prev !== undefined) this.totalWeight -= prev;
    this.creators.set(creator, weight);
    this.totalWeight += weight;
  }

  getCreator(): ObjectCreator<T> {
    let roll = Math.random() * this.totalWeight;
    for (const [creator, weight] of this.creators) {
      roll -= weight;
      if (roll <= 0) return creator;
    }
    return [...this.creators.keys()][0];
  }

  createNew(): T {
    return this.getCreator()();
  }
}
