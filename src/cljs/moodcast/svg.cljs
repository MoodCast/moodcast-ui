(ns moodcast.svg
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as re-frame]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn load-background [id filename]
  (go (let [response (<! (http/get filename {:timeout 500}))
            svg  (if (and response (:body response))
                   (:body response)
                   "not-found")]
        (re-frame/dispatch-sync [:load-background id svg]))))

(defn load-svg [id item state filename]
  (go (let [response (<! (http/get filename {:timeout 500}))
            svg  (if (and response (:body response))
                   (:body response)
                   "not-found")]
        (re-frame/dispatch-sync [:load-svg id item state svg]))))

(defn load-avatar [id body mask body-happy mask-happy]
  (load-svg id :body :normal body)
  (load-svg id :mask :normal mask)
  (load-svg id :body :happy body-happy)
  (load-svg id :mask :happy mask-happy))
