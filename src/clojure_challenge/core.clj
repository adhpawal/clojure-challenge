(ns clojure-challenge.core)
(require '[clojure.java.io :as io])
(require '[clojure.set :as set])

(def ranks [1 2 3 4 5 6 7 8 9 10 11 12 13])
(def suits [{:long "Spades" :short "SP"} {:long "Hearts" :short "HT"}
            {:long "Diamonds" :short "DS"} {:long "Clubs" :short "CL"}])

(defn create-deck []
  (let [counter (atom -1)]
    (for [rank ranks
          suit suits]
      {:rank rank :suit suit :card-id (swap! counter inc)})))

(defn select-cards
  [deck]
  (loop [counter 0
         selected-cards '()
         ]
    (if (< counter 10)
      (recur (inc counter) (conj selected-cards (rand-nth deck)))
      selected-cards
      )
    )
  )

(defn find-remaining-cards
  [selected-cards deck]
  (into [] (set/difference (into #{} deck) (into #{} selected-cards)))
  )

(defn get-long-name
  [card]
  (str (:rank card) " Of " (:long (:suit card)))
  )

(defn get-short-name
  [card]
  (str (:rank card) " " (:short (:suit card)))
  )

(defn print-cards
  [deck]
  (doseq [card deck]
    (println (get-long-name card))
    )
  )

(defn -main
  "Clojure challenge"
  [& args]
  (let [deck (atom nil)
        shuffled-deck (atom nil)
        selected-cards (atom nil)
        remaining-cards (atom nil)
        ]

    ; Create a deck of cards
    (reset! deck (create-deck))

    ; Shuffle the cards
    (reset! shuffled-deck (shuffle @deck))

    ; select 10 cards
    (reset! selected-cards (select-cards @shuffled-deck))
    (println "Selected cards in deck:")
    (print-cards @selected-cards)
    ; find remaining cards
    (reset! remaining-cards (find-remaining-cards @selected-cards @deck))
    (println "Remaining cards in deck:")
    ;(println remaining-cards)
    (print-cards @remaining-cards)
    )
  )



