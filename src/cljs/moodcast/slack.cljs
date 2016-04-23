(ns moodcast.slack
  (:require [re-frame.core :as re-frame]))

(defn on-message [states-js]
  (let [states (js->clj states-js :keywordize-keys true)]
    (doseq [[id mood] states]
      (let [new-state (if (<= mood 0.5) :normal :happy)]
        (re-frame/dispatch [:state-change id new-state])))))

(defn connect []
  (js/Slack on-message))
