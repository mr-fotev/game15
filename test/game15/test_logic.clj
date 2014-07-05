(ns game15.test_logic
  (:use clojure.test game15.logic))

(def test-game-3 (game 3))
(def test-game-4 (game 4))
(def test-game-5 (game 5))
(def solved-game {:size 4 :pos 15 :board (conj (vec (range 1 16)) 0) :moves 0})

(defn test-single [gm n]
  (let [x (dec (sq n))]
    (and (-> gm
             :board
             (count-inversions x)
             even?)
         (= (:pos gm) x)
         (= (:moves gm) 0))))

(deftest test_logic
  (is (= (count-inversions [1 2 3 4 5] 5) 0)
      "First test for inversions")

  (is (= (count-inversions [5 4 3 2 1] 5) 10)
      "Second test for inversions")

  (is (test-single test-game-3 3)
      "Test for a new 3x3 game.")

  (is (test-single test-game-4 4)
      "Test for a new 4x4 game.")

  (is (test-single test-game-5 5)
      "Test for a new 5x5 game.")

  (let [gm3 (-> test-game-3 (move 7) (move 4) (move 3) (move 0))]
    (is (and (= (:pos gm3) 0) (= (:moves gm3) 4))
        "Test for a 3x3 game after 4 moves."))

  (let [gm4 (-> test-game-4 (move 14) (move 10) (move 9) (move 5))]
    (is (and (= (:pos gm4) 5) (= (:moves gm4) 4))
        "Test for a 4x4 game after 4 moves."))

  (let [gm5 (-> test-game-5 (move 23) (move 18) (move 17) (move 12))]
    (is (and (= (:pos gm5) 12) (= (:moves gm5) 4))
        "Test for a 5x5 game after 4 moves."))

  (is (win? solved-game)
      "The solved-game is solved."))
