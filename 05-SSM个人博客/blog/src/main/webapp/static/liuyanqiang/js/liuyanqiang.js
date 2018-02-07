
  (function ($) {
	
	var container;
	
	// 可选颜色
	var colors = ['#96C2F1', '#BBE1F1', '#E3E197', '#F8B3D0', '#FFCC00'];
	
	//创建许愿页
	var createItem = function(commentDate, text, nickname){
		var color = colors[parseInt(Math.random() * 5, 10)]
		$('<div class="item"><div class="commentDate">' + commentDate + 
			'</div><p>'+ text + '</p><a href="javascript:void(0)">'
				+ nickname + '</a></div>'
			).css({ 'background': color }).appendTo(container).drag();
	};
	
	// 定义拖拽函数
    $.fn.drag = function () {
		
        var $this = $(this);
        var parent = $this.parent();
		
        var pw = parent.width();
        var ph = parent.height();
        var thisWidth = $this.width() + parseInt($this.css('padding-left'), 10) + parseInt($this.css('padding-right'), 10);
        var thisHeight = $this.height() + parseInt($this.css('padding-top'), 10) + parseInt($this.css('padding-bottom'), 10);

        var x, y, positionX, positionY;
        var isDown = false; 

        var randY = parseInt(Math.random() * (ph - thisHeight), 10);
        var randX = parseInt(Math.random() * (pw - thisWidth), 10);


        parent.css({
            "position": "relative",
            "overflow": "hidden"
        });
		
        $this.css({
            "cursor": "move",
            "position": "absolute"
        }).css({
            top: randY,
            left: randX
        }).mousedown(function (e) {
            parent.children().css({
                "zIndex": "0"
            });
            $this.css({
                "zIndex": "1"
            });
            isDown = true;
            x = e.pageX;
            y = e.pageY;
            positionX = $this.position().left;
            positionY = $this.position().top;
            return false;
        });
		
		
        $(document).mouseup(function (e) {
            isDown = false;
        }).mousemove(function (e) {
            var xPage = e.pageX;
            var moveX = positionX + xPage - x;

            var yPage = e.pageY;
            var moveY = positionY + yPage - y;

            if (isDown == true) {
                $this.css({
                    "left": moveX,
                    "top": moveY
                });
            } else {
                return;
            }
            if (moveX < 0) {
                $this.css({
                    "left": "0"
                });
            }
            if (moveX > (pw - thisWidth)) {
                $this.css({
                    "left": pw - thisWidth
                });
            }
            if (moveY < 0) {
                $this.css({
                    "top": "0"
                });
            }
            if (moveY > (ph - thisHeight)) {
                $this.css({
                    "top": ph - thisHeight
                });
            }
        });
    };
	
	// 初始化
	var init = function () {
		
		container = $('#container');
		
		// 绑定关闭事件
		//container.on('click','a',function () {
			//$(this).parent().remove();
		//}).height($(window).height() -204);

		$.each(comments, function (i,c) {
			createItem(c.commentDate, c.content, c.nickname);
		});
		
		// 绑定输入框
		$('#input').keydown(function (e) {
			var $this = $(this);
			if(e.keyCode == '13') {
				var value = $this.val();
				if(value) {
					createItem(value);
					$this.val('');
				}
			}
		});
		
	};
	
	$(function() {
		init();
	});
	
})(jQuery);
