export interface Diary {
  image: string,
  year: string,
  director: string,
  id: number,
  title: string,
  like: boolean,
  rate: number
  timestamp: { day: string, month: string, dayOfMonth: string}
}
