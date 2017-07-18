<div class="row">
        <div class="col-xs-12">
            <nav class="navbar navbar-inverse navbar-embossed" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-01">
                        <span class="sr-only">Toggle navigation</span>
                    </button>
                    <a class="navbar-brand" href="/">车辆管理系统&nbsp;&nbsp;</a>
                </div>
                <div class="collapse navbar-collapse" id="navbar-collapse-01">
                    <ul class="nav navbar-nav navbar-left" id="mainMenu">
                        <li><a href="/fence/list">围栏</a></li>
                        <li class="dropdown" id="instanceMenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Instance <b class="caret"></b></a>
                            <span class="dropdown-arrow"></span>
                            <ul class="dropdown-menu">
                                <li><a href="/instance/instance_form.hb">New Instance</a></li>
                                <li><a href="/instance/list.hb">Instances</a></li>
                            </ul>
                        </li>
                        <li class="dropdown" id="logMenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Log <b class="caret"></b></a>
                            <span class="dropdown-arrow"></span>
                            <ul class="dropdown-menu">
                                <li><a href="/log/list.hb">Monitor Log</a></li>
                                <li><a href="/log/reminder_list.hb">Reminder Log</a></li>
                            </ul>
                        </li>
                        <li class="dropdown" id="systemMenu">
                       		<a href="#" class="dropdown-toggle" data-toggle="dropdown">System <b class="caret"></b></a>
                     		<span class="dropdown-arrow"></span>
                         	<ul class="dropdown-menu">
	                            <li><a href="/user/list.hb">Users</a></li>
                                <li><a href="/user/weixin_list.hb">WeChat Users</a></li>
                                <li><a href="/system/setting.hb">Setting</a></li>
                      		</ul>
                     	</li>
                        <li id="userProfileMenu">
                       		<a href="/user_profile.hb">hb</a>
                     	</li>
                        <li>
                        	<a href="/logout" title="退出"><em class="fui-exit text-danger"></em></a>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-right" action="/search.hb" role="search">
                        <div class="form-group">
                            <div class="input-group">
                                <input class="form-control" id="navbarInput-01" name="key" type="search"
                                       placeholder="Search"/>
                                <span class="input-group-btn">
                                    <button type="submit" class="btn"><span class="fui-search"></span></button>
                                </span>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
            </nav>
            <!-- /navbar -->
        </div>
    </div>
    <!-- /row -->