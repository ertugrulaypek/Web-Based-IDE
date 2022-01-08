export class Project {
  id: string;
  name: string;
  userIds: string[];

  constructor(name: string) {
    this.name = name;
  }
}
