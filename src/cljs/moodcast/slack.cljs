(ns moodcast.slack)

(defn on-message [states]
  (doseq [[id mood] states]
    (let [new-state (if (< new-state 0) :normal :happy)]
      (re-frame/dispatch [:state-change id new-state]))))

(defn connect []
  (.-getSlackData (js/slack) on-message))
