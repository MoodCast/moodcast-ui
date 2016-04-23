(ns moodcast.img
  (:require [re-frame.core :as re-frame]))

(defn load-face [id filename]
  (re-frame/dispatch [:load-face id filename]))

