(ns moodcast.slack
  (:require [re-frame.core :as re-frame]))

(defn on-message [states]
  (doseq [[id mood] states]
    (let [new-state (if (< mood 0) :normal :happy)]
      (re-frame/dispatch [:state-change id new-state]))))

(defn connect []
  #_(.getSlackData (js/slack) on-message))
