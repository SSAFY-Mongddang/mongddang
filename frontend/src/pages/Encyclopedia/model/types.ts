
export interface ICharacterData {
  id: number;
  name: string;
  story: string;
  price: number;
  isOwned: boolean;
  isNew: boolean;
  isMain: boolean;
}

export interface ICharacterInfo {
  code: string;
  message: string;
  data: ICharacterData[];
}

export interface INewInfoResponse {
  code: number;
  message: string;
}

export interface CharacterResponse {
  data: {
    data: ICharacterData[];
  };
}