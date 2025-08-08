
export interface OffertModel {
  id: number;
  name: string;
  price: number;
  max_People: number;
  availableSeats: number;
  description: string;
  location?: string;
  date?: string;
  imageUrl?: string;
 
}