export interface AuctionLot {
    auctionId: number;
    lotId: number;
    title: string;
    description: string;
    imagePath: string;
    estimate: number;
    currentBid: number;
    endTime: string;
}