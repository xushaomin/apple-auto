/**
 * All JS in here.
 */


$(function () {
    new HeartBeat();
});


function HeartBeat() {
    this.toggleMenuActive();
}

HeartBeat.prototype = {
    toggleMenuActive: function () {
        var href = location.pathname;
        if (href.indexOf("instance/") != -1) {
            $("#mainMenu li#instanceMenu").addClass("active");
        } else if (href.indexOf("log/") != -1) {
            $("#mainMenu li#logMenu").addClass("active");
        } else if (href.indexOf("config/") != -1) {
            $("#mainMenu li#configMenu").addClass("active");
        } else if (href.indexOf("user/") != -1 || href.indexOf("system/") != -1) {
            $("#mainMenu li#systemMenu").addClass("active");
        } else if (href.indexOf("user_profile") != -1) {
            $("#mainMenu li#userProfileMenu").addClass("active");
        } else {
            $("#mainMenu li:eq(0)").addClass("active");
        }
    },
    initialAlert: function (eleId, fadeInTime, fadeOutTime) {
        if ('' != eleId) {
            fadeInTime = fadeInTime || 1000;     //default fade in: 1000ms
            fadeOutTime = fadeOutTime || 1000; //default fade out: 1000ms
            var $alert = $("#" + eleId);
            $alert.show().parent().parent().fadeIn(fadeInTime).slideUp(fadeOutTime);
        }
    }
};


/**
 * displaytag paginated use it.
 * Don't change it
 *
 * @param formId
 * @param data
 */
function displaytagform(formId, data) {
    var $form = $("#" + formId);
    var action = $form.attr("action");
    var params = action.indexOf('?') == -1 ? '?' : '&';
    $.map(data, function (d) {
        params += (d.f + "=" + d.v + "&");
    });

    var url = action + params;
    var $targetDiv = $("div.displayTarget");
    if ($targetDiv.length > 0) {
        //if exist, load  the content to the div
        $targetDiv.load(url);
    } else {
        location.href = url;
    }
}


/**
 * instance_list.jsp
 * @param alert
 * @constructor
 */
function InstanceList(alert) {
    HeartBeat.prototype.initialAlert(alert);
}


/**
 * user_profile.jsp
 * @param alert
 * @constructor
 */
function UserProfile(alert) {
    HeartBeat.prototype.initialAlert(alert);
}


/**
 * system_setting.jsp
 * @param alert
 * @constructor
 */
function SystemSetting(alert) {
    HeartBeat.prototype.initialAlert(alert);
    this.executeCleanLog();
}

SystemSetting.prototype = {
    executeCleanLog: function () {
        $("a#executeCleanLog").click(function () {
            $("#executeCleanLogForm").submit();
        });
    }
};


/**
 * user_list.jsp
 * @param alert
 * @constructor
 */
function UserList(alert) {
    HeartBeat.prototype.initialAlert(alert);
    this.resetPass();
    this.deleteUser();
}

UserList.prototype = {
    resetPass: function () {
        $("a.resetPass").click(function () {
            var $this = $(this);
            if (!confirm($this.attr("confirmText"))) {
                return
            }
            var guid = $this.attr("guid");
            $.post("reset_password/" + guid + ".hb", function (data) {
                var $modal = $("#resetPassModal");
                $modal.find("div.modal-body").html(data);
                $modal.modal("show");
            });

        });
    },
    deleteUser: function () {
        $("a.deleteUser").click(function () {
            var $this = $(this);
            if (!confirm($this.attr("confirmText"))) {
                return
            }
            var guid = $this.attr("guid");
            $.post("delete/" + guid + ".hb", function (data) {
                if ('ok' === data) {
                    location.href = location.pathname + "?alert=deleteSuccess";
                }
            });

        });
    }

};


/**
 * index.jsp
 * @param alert
 * @constructor
 */
function Index(alert) {
    HeartBeat.prototype.initialAlert(alert);
    this.submitSearch();
}

Index.prototype = {
    submitSearch: function () {
        $("select[name='maxResult']").change(function () {
            $(this.form).submit();
        });
        $("input#enabled").change(function () {
            var $this = $(this);
            $this.next().val($this.is(":checked"));
            $(this.form).submit();
        });

    }
};


/**
 * instance_form.jsp
 * @constructor
 */
function InstanceForm() {
    this.addParam();
    this.deleteParam();

    this.checkRandom();
}

InstanceForm.prototype = {
    checkRandom: function () {
        $("form").on("click", "input.random", function () {
            var $this = $(this);
            if ($this.is(":checked")) {
                $this.parent().prev().val("").attr("readonly", true);
            } else {
                $this.parent().prev().attr("readonly", false);
            }
        });
    },
    addParam: function () {
        $("form").on("click", "a.addParam", function () {
            var $this = $(this);
            var $tr = $this.parent().parent();
            var order = $tr.attr("order");

            var newOrder = parseInt(order) + 1;
            var $newTr = $tr.clone().attr("order", newOrder);

            var $keyEle = $newTr.find("td input:first");
            $keyEle.attr("id", "urlParameters" + newOrder + ".key").attr("name", "urlParameters[" + newOrder + "].key");

            var $valueEle = $newTr.find("td .input-group input.value");
            $valueEle.attr("id", "urlParameters" + newOrder + ".value").attr("name", "urlParameters[" + newOrder + "].value");

            var $randomEle = $newTr.find("td input:checkbox");
            $randomEle.attr("name", "urlParameters[" + newOrder + "].randomValue");

            $newTr.find("td:last").find("a").removeClass("hidden");
            $tr.after($newTr);
            $this.addClass("hidden").next().addClass("hidden");
        });
    },
    deleteParam: function () {
        $("form").on("click", "a.deleteParam", function () {
            var $tr = $(this).parent().parent();
            var order = $tr.attr("order");

            var $prevLastTd = $tr.prev().find("td:last");
            $prevLastTd.find("a.addParam").removeClass("hidden");
            if (order != "1") {
                $prevLastTd.find("a.deleteParam").removeClass("hidden");
            }

            $tr.remove();
        });
    }
};


/**
 * monitoring_instance.jsp
 * @constructor
 */
function MonitoringInstance(guid) {
    this.loadInstanceStatics(guid)
}

MonitoringInstance.prototype = {
    loadInstanceStatics: function (guid) {
        var self = this;
        self._loadCurrInsStatics(guid);

        setInterval(function () {
            self._loadCurrInsStatics(guid);
        }, 60000); //60 seconds
    },
    _loadCurrInsStatics: function (guid) {
        var $staticsDiv = $("div#staticsDiv");
        var $loading = $staticsDiv.prev().removeClass("hidden");

        $staticsDiv.load("statistics/" + guid + ".hb", function () {
            $loading.addClass("hidden");
        });
    }
};

/**
 * search_result.jsp
 * @constructor
 */
function SearchResult() {
    this.toggleTab();
}

SearchResult.prototype = {
    toggleTab: function () {
        $(".nav-tabs li a").click(function () {
            $("#searchType").val($(this).attr("sType"));
            $("#filterForm").submit();
        });
    }
};

/**
 * reminder_logs.jsp
 * @constructor
 */
function ReminderLogs() {
    this.showEmailContent();
    this.showWeChatContent();
}

ReminderLogs.prototype = {
    showEmailContent: function () {
        $("a.showMailContent").click(function () {
            $("div#modalContainer").html($(this).next().html());
            $("h4#myModalLabel").html("提醒的邮件内容");
            $("button#modalConfirmBtn").hide();
            $("div#myModal").modal("show");
        });
    },
    showWeChatContent: function () {
        $("a.showWeChatContent").click(function () {
            $("div#modalContainer").html($(this).next().html());
            $("h4#myModalLabel").html("微信收到的提醒内容");
            $("button#modalConfirmBtn").hide();
            $("div#myModal").modal("show");
        });
    }
};
