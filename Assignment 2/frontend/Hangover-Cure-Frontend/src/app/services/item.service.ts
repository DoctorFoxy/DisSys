import { Injectable } from "@angular/core";
import { Item } from "../models/item.model";

@Injectable({
    providedIn: 'root'
})
export class ItemService {

    fetchItems(): Item[] {
        return [
            {
                id: '0',
                itemid1: 'asadf',
                itemqty1: 1,
                itemid2: '1234',
                itemqty2: 1,
                title: 'Basic Hangover Cure',
                description: 'Cheap and effective: Our most popular product for general cases. Featuring an electrolyte mix as well as some fresh fruit to get you back on your feet.',
                imagelink: '/img/BasicHangoverCure.png',
                price: 10.0
            },
            {
                id: '1',
                itemid1: 'anghff',
                itemqty1: 1,
                itemid2: '1535',
                itemqty2: 1,
                title: 'Light Brainfog Cure',
                description: 'For those nights where you really only had a couple beers and now want to give you a tiny oomph: A small electrolyte mix and a banana.',
                imagelink: '/img/LightBrainfogCure.png',
                price: 6.0
            },
            {
                id: '2',
                itemid1: 'asadf',
                itemqty1: 2,
                itemid2: '8587',
                itemqty2: 1,
                title: 'Dehydration Cure',
                description: 'For when you have neglected your hydration: 2x electrolyte mix with 2L of orange juice. Get ready to start peeing!',
                imagelink: '/img/DehydrationCure.png',
                price: 15.0
            },
            {
                id: '3',
                itemid1: 'asadf',
                itemqty1: 1,
                itemid2: '57782',
                itemqty2: 1,
                title: 'Productivity Boost',
                description: 'When your alarm rings at 6am for work, you feel the brainfog and just stare in the mirror, scared of your performance at work: This is the cure! Comes with an electrolyte mix and a six pack of energy drinks.',
                imagelink: '/img/ProductivityBoost.png',
                price: 20.0
            },
            {
                id: '4',
                itemid1: 'sadf',
                itemqty1: 1,
                itemid2: '5422',
                itemqty2: 1,
                title: 'Smoker\'s Cure',
                description: 'Can\'t control yourself around those pesky cigarettes? Can you feel like your lungs struggling for air and making weird noises? Comes with a small asthma inhaler, and high peppermint bubblegum',
                imagelink: '/img/SmokersCure.png',
                price: 25.0
            },
            {
                id: '5',
                itemid1: 'asgijf',
                itemqty1: 1,
                itemid2: '1684',
                itemqty2: 1,
                title: 'Stuck in bed package',
                description: 'Your head hurts and you can\'t get out of bed because everything in the world is hostile to your senses right now? We understand, you need to refuel your body with some precious fast food: Comes with an electrolyte mix, one ibuprofen and a juicy Döner Kebab.',
                imagelink: '/img/StuckInBed.png',
                price: 25.0
            },
            {
                id: '6',
                itemid1: 'afwgw',
                itemqty1: 1,
                itemid2: '16886',
                itemqty2: 1,
                title: 'My body is on the brink of death package',
                description: 'Title says it all. 2x electrolyte mix, 1x extra strong ibuprofen, 1x Pizza, 1x Döner, 1x Beer to counter the hangover and 2L of orange juice to hydrate.',
                imagelink: '/img/MyBodyIsOnTheBrinkOfDeath.png',
                price: 50.0
            },
        ];
    }
}