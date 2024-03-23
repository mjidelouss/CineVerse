export interface Diary {
  content: string
  like: boolean
  id: number
  image: string
  language: string
  title: string
  rating: number
  reviewId: number
  timestamp: { day: string, month: string, dayOfMonth: string}
  year: number
}
