export interface Diary {
  reviewId: number
  image: string,
  year: string,
  director: string,
  id: number,
  content: string,
  title: string,
  like: boolean,
  rate: number
  timestamp: { day: string, month: string, dayOfMonth: string}
}
