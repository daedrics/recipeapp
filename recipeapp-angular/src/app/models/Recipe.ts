export interface Recipe {
    id?: number;
    name: string;
    time: number;
    published: boolean;
    publishedOn: string;
    imageUrl: string;
    description: string;
    calories: number;
    servings: number;
    user: any;
}