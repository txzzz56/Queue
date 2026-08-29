package org.example.demo;

import java.util.Arrays;
/** Denne delen av koden håndterer hvordan køen fungerer, 
 * inkludert hvordan elementer legges til, fjernes, vises og sorteres
 */
public class Queue {

    private int[] queue; /* Her lagre
    s elementene i køen */

    private int front; /* Lagrer indeksen til det første elementet i køen */
    private int rear; /* Lagrer indeksen til det siste elementet i køen */
    private int size; /* Lagrer maksimal størrelse på køen */
    private int count; /* Lagrer antall elementer i køen */

    //* Konstruktør for å opprette en kø med gitt størrelse */
    public Queue(int size) {

        this.size = size;

        queue = new int[size];

        front = 0;
        rear = -1;
        count = 0;
    }

    public boolean isFull() { /* Sjekker om køen er full */

        return count == size;
    }

    public boolean isEmpty() { /* Sjekker om køen er tom */

        return count == 0;
    }

    public String enqueue(int item) { /* Legger til et element i køen */

        if (isFull()) {

            return "Queue is full."; /* Hvis køen er full, returnerer melding */
        }

        rear = (rear + 1) % size;

        queue[rear] = item;

        count++;

        return "Enqueued: " + item;
    }

    public String dequeue() { /* Fjerner et element fra køen */

        if (isEmpty()) {

            return "Queue is empty."; /* Hvis køen er tom, returnerer melding */
        }

        int item = queue[front];

        front = (front + 1) % size;

        count--;

        return "Dequeued: " + item;
    }

    public String displayQueue() { /* Viser elementene i køen */

        if (isEmpty()) {

            return "Queue is empty."; /* Hvis køen er tom, returnerer melding */
        }

        StringBuilder sb = new StringBuilder(); /* Streng for å beregne riktig sirkulær rekkefølge */

        for (int i = 0; i < count; i++) {

            int index = (front + i) % size;

            sb.append(queue[index]).append(" ");
        }

        return sb.toString();
    }

    public int[] getSortedQueue() { /* Henter en sortert versjon av køen */

        int[] sortedQueue = new int[count];

        for (int i = 0; i < count; i++) {

            int index = (front + i) % size;

            sortedQueue[i] = queue[index];
        }

        Arrays.sort(sortedQueue);

        return sortedQueue; /* Returnerer den sorterte køen */
    }

    public int getFront() { /* Henter indeksen til det første elementet i køen */

        return front;
    }

    public int getRear() { /* Henter indeksen til det siste elementet i køen */

        return rear;
    }

    public int getCount() { /* Henter antall elementer i køen */

        return count;
    }
}
