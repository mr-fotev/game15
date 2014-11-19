(ns game15.logic)

(defn sq [x] (* x x))

(defn count-inversions [v n]
  "Counts the inversions in vector v with size n."
  (apply + (for [i (range (dec n)), j (range (inc i) n)] (if (> (v i) (v j)) 1 0))))

(defn solvable-board [s]
  "Makes a solvable board with size SxS."
  (let [n (sq s), v (shuffle (range 1 n))]
    (if (even? (count-inversions v (dec n)))
      (conj v 0)
      (conj (vec (conj (drop 2 v) (first v) (second v))) 0))))

(defn move [g n]
  "Moves game g on index n."
  (let [s (:size g), brd (:board g), i (quot n s), j (rem n s)]
    (letfn [(pos? [i1 j1 x] (and (< -1 i1 s) (< -1 j1 s) (= (:pos g) x)))
      (res [x] {:size s, :pos n, :board (assoc brd x (brd n), n 0), :moves (inc (:moves g))})]
        (cond
          (pos? i (inc j) (inc n)) (res (inc n))
          (pos? (dec i) j (- n s)) (res (- n s))
          (pos? i (dec j) (dec n)) (res (dec n))
          (pos? (inc i) j (+ n s)) (res (+ n s))
          :else g))))

(defn win? [g]
  "Checks if the player has solved the board."
  (= (:board g) (conj (->> g :size sq (range 1) vec) 0)))

(defn game [s]
  "Defines the game as a map."
  {:size s, :pos (dec (sq s)), :board (solvable-board s), :moves 0})
