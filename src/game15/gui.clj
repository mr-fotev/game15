(ns game15.gui (:use [seesaw core color graphics behave] game15.logic))

(def game-window (frame :title "15 Puzzle Deluxe", :height 400, :width 600, :on-close :exit, :resizable? false))

(def x (atom (game 4)))

(defn init [s]
  "Initializes the board, some buttons and a label."
  (let [brd (:board @x)]
    (xyz-panel :items
               (conj
                (vec (for [i (range s), j (range s), :let [d (+ (* s i) j)], :when (not (= d (:pos @x)))]
                       (button :text (str (brd d))
                               :foreground :white
                               :font "ARIAL-BOLD-20"
			       :background (if (even? (+ (quot (dec (brd d)) s)
                                                         (rem (dec (brd d)) s)))
                                             (color 128 0 255)
                                             (color 190 190 250))
			       :bounds [(+ 40 (* j (inc (quot 300 s))))
                                        (+ 35 (* i (inc (quot 300 s))))
                                        (quot 300 s)
                                        (quot 300 s)]
			       :listen [:action (fn [e] (do
                                                          (swap! x move d)
                                                          (config! game-window :content (init s))
                                                          (when (win? @x)
                                                            (alert (format "Hey there,... you win in %d moves!" (:moves @x)))
                                                            (swap! x (fn [y] (game s)))
                                                            (config! game-window :content (init s)))))])))

                (button :text "New 3x3"
                        :foreground :white
                        :font "ARIAL-BOLD-18"
			:background :blue
			:bounds [400 100 150 60]
			:listen [:action (fn [e] (do
                                                   (swap! x (fn [y] (game 3)))
                                                   (config! game-window :content (init 3))))])

		(button :text "New 4x4"
                        :foreground :white
                        :font "ARIAL-BOLD-18"
			:background :blue
		        :bounds [400 170 150 60]
			:listen [:action (fn [e] (do
                                                   (swap! x (fn [y] (game 4)))
                                                   (config! game-window :content (init 4))))])

		(button :text "New 5x5"
                        :foreground :white
                        :font "ARIAL-BOLD-18"
			:background :blue
			:bounds [400 240 150 60]
			:listen [:action (fn [e] (do
                                                   (swap! x (fn [y] (game 5)))
                                                   (config! game-window :content (init 5))))])

                (label  :text (format "Moves: %d" (:moves @x))
                        :foreground :black
                        :font "ARIAL-BOLD-18"
			:bounds [400 40 150 50])

                (label  :icon (clojure.java.io/file "resources/pic.png")
                        :bounds [0 0 600 400])))))

(config! game-window :content (init 4))

(defn display-game [] (show! game-window))
