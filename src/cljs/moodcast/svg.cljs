(ns moodcast.svg
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as re-frame]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn load-svg [item state filename]
  (go (let [response (<! (http/get filename {:timeout 500}))
            svg  (if (and response (:body response))
                   (:body response)
                   "not-found")]
        (re-frame/dispatch [:load-svg filename item state svg]))))

(defn load-avatar [body mask body-happy mask-happy]
  (load-svg :body :normal body)
  (load-svg :mask :normal mask)
  (load-svg :body :happy body-happy)
  (load-svg :mask :happy mask-happy))
